package agile.aresback.service.Impl;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.exception.PaymentException;
import agile.aresback.mapper.AppPaymentMapper;
import agile.aresback.model.entity.AppPayment;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.enums.StateReservation;
import agile.aresback.model.enums.StateReservationClient;
import agile.aresback.model.enums.StatusPago;
import agile.aresback.repository.AppPaymentRepository;
import agile.aresback.service.AppPaymentService;
import agile.aresback.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;


import java.util.Base64;
import java.util.Collections;

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
        try {
            MercadoPagoConfig.setAccessToken(mpAccessToken);

            if (dto.getTitle() == null || dto.getTitle().isBlank()) {
                dto.setTitle("Reserva PACHA");
            }

            // Validación: se requiere reservationId
            if (dto.getReservationId() == null) {
                throw new PaymentException("Debe proporcionar el ID de la reserva para vincular el pago.");
            }

            Reservation reservation = reservationService.findById(dto.getReservationId())
                    .orElseThrow(() -> new PaymentException("No se encontró la reserva con el ID proporcionado."));

            // Validación: el campo externalReference es obligatorio
            String externalRef = dto.getExternalReference();
            if (externalRef == null || externalRef.isBlank()) {
                throw new PaymentException("El campo externalReference es obligatorio para vincular la reserva.");
            }

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(dto.getTitle())
                    .quantity(dto.getQuantity())
                    .unitPrice(dto.getUnitPrice())
                    .build();

            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .email(dto.getEmail())
                    .build();

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://miweb.com/pago-exitoso")
                    .pending("https://miweb.com/pago-pendiente")
                    .failure("https://miweb.com/pago-fallido")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(Collections.singletonList(item))
                    .payer(payer)
                    .backUrls(backUrls)
                    .notificationUrl(backendBaseUrl + "/api/v1/mercado-pago/webhook")
                    .autoReturn("approved")
                    .externalReference(externalRef)
                    .build();

            PreferenceClient preferenceClient = new PreferenceClient();
            Preference preference = preferenceClient.create(preferenceRequest);

            // Aquí se construye el pago con la reserva vinculada
            AppPayment payment = appPaymentMapper.toEntityWithReservation(dto, reservation);
            payment.setPreferenceId(preference.getId());
            payment.setStatusPago(StatusPago.CREADO);
            payment.setExternalReference(externalRef);

            AppPayment saved = paymentRepository.save(payment);
            return appPaymentMapper.toDTO(saved);

        } catch (MPApiException e) {
            throw new PaymentException("Error de API al crear preferencia: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            throw new PaymentException("Error al crear preferencia de pago: " + e.getMessage());
        } catch (Exception e) {
            throw new PaymentException("Error general al crear preferencia: " + e.getMessage());
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

        if (payment.getReservation() == null) {
            Integer reservationId;
            try {
                reservationId = decodeReferenceToReservationId(externalReference);
            } catch (Exception e) {
                throw new PaymentException("Error al decodificar el externalReference: " + e.getMessage());
            }

            Reservation reservation = reservationService.findById(reservationId)
                    .orElseThrow(() -> new PaymentException("No se encontró la reserva temporal vinculada"));

            if (reservation.getStateReservation() != StateReservation.ANULADA &&
                    reservation.getStateReservation() != StateReservation.RESERVADA) {

                reservation.setStateReservation(StateReservation.RESERVADA);
                reservation.setStateReservationClient(StateReservationClient.EN_ESPERA);
                payment.setReservation(reservation);
                payment.setPaymentId(paymentId);
                payment.setStatusPago(StatusPago.APROBADO);

                reservationService.createReservation(reservation);
                paymentRepository.save(payment);

            } else {
                throw new PaymentException("La reserva ya fue procesada o anulada.");
            }
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

}
