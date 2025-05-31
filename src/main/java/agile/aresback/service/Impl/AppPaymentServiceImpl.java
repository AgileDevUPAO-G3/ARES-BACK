package agile.aresback.service.Impl;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.exception.PaymentException;
import agile.aresback.mapper.AppPaymentMapper;
import agile.aresback.model.entity.AppPayment;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.enums.StateReservation;
import lombok.extern.slf4j.Slf4j;
import agile.aresback.model.enums.StateReservationClient;
import agile.aresback.model.enums.StatusPago;
import agile.aresback.repository.AppPaymentRepository;
import agile.aresback.service.AppPaymentService;
import agile.aresback.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import java.util.List;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import java.util.Base64;
import java.util.Collections;

@Slf4j
@Service
public class AppPaymentServiceImpl implements AppPaymentService {

    private final AppPaymentRepository paymentRepository;
    private final ReservationService reservationService;
    private final AppPaymentMapper appPaymentMapper;
    private final ObjectMapper objectMapper;

    @Value("${mercadopago.access.token}")
    private String mpAccessToken;

    @Value("${backend.base.url}")
    private String backendBaseUrl;

    public AppPaymentServiceImpl(AppPaymentRepository paymentRepository,
                                 ReservationService reservationService,
                                 AppPaymentMapper appPaymentMapper,
                                 ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.reservationService = reservationService;
        this.appPaymentMapper = appPaymentMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public AppPaymentDTO createPreference(AppPaymentDTO dto) {
        log.info("[PAGO] Iniciando creación de preferencia con DTO: {}", dto);

        if (dto.getReservationId() == null) {
            throw new PaymentException("Debe proporcionar el ID de la reserva para vincular el pago.");
        }

        Reservation reservation = reservationService.findById(dto.getReservationId())
                .orElseThrow(() -> new PaymentException("No se encontró la reserva con el ID proporcionado."));

        if (dto.getExternalReference() == null || dto.getExternalReference().isBlank()) {
            throw new PaymentException("El campo externalReference es obligatorio para vincular la reserva.");
        }

        try {
            MercadoPagoConfig.setAccessToken(mpAccessToken);

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(dto.getTitle())
                    .quantity(dto.getQuantity())
                    .unitPrice(dto.getUnitPrice())
                    .currencyId("PEN")
                    .build();

            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .email(dto.getEmail())
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .payer(payer)
                    .externalReference(dto.getExternalReference())
                    .notificationUrl(backendBaseUrl + "/api/mercado-pago/webhook")
                    .build();


            PreferenceClient preferenceClient = new PreferenceClient();
            Preference preference = preferenceClient.create(preferenceRequest);

            AppPayment payment = appPaymentMapper.toEntityWithReservation(dto, reservation);
            payment.setPreferenceId(preference.getId());
            payment.setStatusPago(StatusPago.CREADO);
            payment.setExternalReference(dto.getExternalReference());

            AppPayment saved = paymentRepository.save(payment);
            AppPaymentDTO responseDTO = appPaymentMapper.toDTO(saved);

            responseDTO.setInitPoint(preference.getInitPoint());

            log.info("[PAGO] Preferencia creada correctamente con ID: {}", preference.getId());
            return responseDTO;

        } catch (MPApiException e) {
            log.error("[PAGO] Error de API de MercadoPago: {}", e.getApiResponse().getContent());
            throw new PaymentException("Error de API al crear la preferencia: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            log.error("[PAGO] Error general de MercadoPago: {}", e.getMessage());
            throw new PaymentException("Error general al crear la preferencia: " + e.getMessage());
        }
    }


    @Override
    public AppPaymentDTO findByPreferenceId(String preferenceId) {
        AppPayment payment = paymentRepository.findAll().stream()
                .filter(p -> preferenceId.equals(p.getPreferenceId()))
                .findFirst()
                .orElseThrow(() -> new PaymentException("No se encontró el pago con preferencia: " + preferenceId));
        return appPaymentMapper.toDTO(payment);
    }

    @Override
    public void linkReservationAfterPaymentApproved(String externalReference, Long paymentId) {
        AppPayment payment = paymentRepository.findByExternalReference(externalReference)
                .orElseThrow(() -> new PaymentException("No se encontró el pago con externalReference: " + externalReference));

        Reservation reservation = payment.getReservation();
        if (reservation == null) {
            Integer reservationId;
            try {
                reservationId = decodeReferenceToReservationId(externalReference);
            } catch (Exception e) {
                throw new PaymentException("Error al decodificar el externalReference: " + e.getMessage());
            }

            reservation = reservationService.findById(reservationId)
                    .orElseThrow(() -> new PaymentException("No se encontró la reserva temporal vinculada"));
            payment.setReservation(reservation);
        }

        if (reservation.getStateReservation() != StateReservation.ANULADA &&
                reservation.getStateReservation() != StateReservation.RESERVADA) {

            reservation.setStateReservation(StateReservation.RESERVADA);
            reservation.setStateReservationClient(StateReservationClient.EN_ESPERA);
            payment.setPaymentId(paymentId);
            payment.setStatusPago(StatusPago.APROBADO);

            reservationService.createReservation(reservation);
            paymentRepository.save(payment);

            log.info("[CONFIRMACION MANUAL] Reserva actualizada y vinculada al pago correctamente.");
        } else {
            log.warn("[CONFIRMACION MANUAL] La reserva ya fue procesada o anulada.");
        }
    }


    @Override
    public AppPaymentDTO actualizarPago(AppPaymentDTO dto) {
        AppPayment payment = paymentRepository.findById(dto.getId())
                .orElseThrow(() -> new PaymentException("Pago no encontrado"));

        StatusPago nuevoEstado = dto.getStatusPago(); // ya no necesitas valueOf()
        payment.setStatusPago(nuevoEstado);

        return appPaymentMapper.toDTO(paymentRepository.save(payment));
    }

    // ===================== UTILIDADES =====================

    private String encodeReservationIdToReference(Integer reservationId) throws Exception {
        return Base64.getEncoder().encodeToString(objectMapper.writeValueAsBytes(reservationId));
    }

    private Integer decodeReferenceToReservationId(String reference) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(reference);
        return objectMapper.readValue(decoded, Integer.class);
    }

    @Override
    public Payment consultarPagoEnMercadoPago(Long paymentId) {
        try {
            MercadoPagoConfig.setAccessToken(mpAccessToken);
            PaymentClient client = new PaymentClient();
            return client.get(paymentId);
        } catch (MPApiException e) {
            // Errores detallados provenientes del backend de MercadoPago
            throw new PaymentException("Error de API al consultar el pago: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            // Errores generales relacionados con la SDK o configuración
            throw new PaymentException("Error general al consultar el pago en MercadoPago: " + e.getMessage());
        }
    }

    @Override
    public void actualizarPagoDesdeWebhook(String paymentIdStr) {
        try {
            Long paymentId = Long.parseLong(paymentIdStr);
            Payment pagoMp = consultarPagoEnMercadoPago(paymentId);

            if ("approved".equalsIgnoreCase(pagoMp.getStatus())) {
                String externalRef = pagoMp.getExternalReference();
                log.info("[WEBHOOK] Pago aprobado recibido. externalReference: {}, paymentId: {}", externalRef, paymentId);

                linkReservationAfterPaymentApproved(externalRef, paymentId);
            } else {
                log.info("[WEBHOOK] Pago recibido con estado no aprobado: {}", pagoMp.getStatus());
            }
        } catch (Exception e) {
            log.error("[WEBHOOK] Error al procesar el webhook de pago: {}", e.getMessage());
        }
    }


}
