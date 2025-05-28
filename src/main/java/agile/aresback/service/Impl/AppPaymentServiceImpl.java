package agile.aresback.service.Impl;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.exception.PaymentException;
import agile.aresback.mapper.AppPaymentMapper;
import agile.aresback.model.entity.AppPayment;
import agile.aresback.model.entity.Reservation;
import agile.aresback.repository.AppPaymentRepository;
import agile.aresback.service.AppPaymentService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AppPaymentServiceImpl implements AppPaymentService {

    private final AppPaymentRepository paymentRepository;
    private final AppPaymentMapper paymentMapper;

    @Value("${mercadopago.access-token}")
    private String accessToken;

    public AppPaymentServiceImpl(AppPaymentRepository paymentRepository, AppPaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public AppPaymentDTO createPreference(AppPaymentDTO dto, Reservation reservation) {
        // Validación básica de datos de entrada
        if (dto.getTitle() == null || dto.getTitle().isBlank())
            throw new PaymentException("El título del pago es obligatorio.");

        if (dto.getQuantity() == null || dto.getQuantity() <= 0)
            throw new PaymentException("La cantidad debe ser mayor a cero.");

        if (dto.getUnitPrice() == null || dto.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new PaymentException("El precio debe ser mayor a cero.");

        if (dto.getEmail() == null || dto.getEmail().isBlank())
            throw new PaymentException("El correo electrónico es obligatorio.");

        try {
            // Configura Mercado Pago
            MercadoPagoConfig.setAccessToken(accessToken);

            // Crear ítem
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .quantity(dto.getQuantity())
                    .unitPrice(dto.getUnitPrice())
                    .currencyId("PEN")
                    .build();

            // Crear comprador
            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .email(dto.getEmail())
                    .build();

            // Crear preferencia
            PreferenceRequest request = PreferenceRequest.builder()
                    .items(List.of(item))
                    .payer(payer)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(request);

            // Mapear DTO a entidad y completar datos
            AppPayment payment = paymentMapper.toEntity(dto);
            payment.setPreferenceId(preference.getId());
            payment.setStatus("created");
            payment.setReservation(reservation);

            // Guardar en base de datos
            AppPayment saved = paymentRepository.save(payment);

            // Devolver DTO con init_point
            return paymentMapper.toDTO(saved, preference.getInitPoint());

        } catch (Exception e) {
            throw new PaymentException("Error al crear preferencia en Mercado Pago: " + e.getMessage());
        }
    }
}
