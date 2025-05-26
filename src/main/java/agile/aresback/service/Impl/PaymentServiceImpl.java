package agile.aresback.service.Impl;

import agile.aresback.dto.PaymentDTO;
import agile.aresback.exception.PaymentException;
import agile.aresback.mapper.PaymentMapper;
import agile.aresback.model.entity.Payment;
import agile.aresback.repository.PaymentRepository;
import agile.aresback.service.PaymentService;
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
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Value("${mercadopago.access-token}")
    private String accessToken;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentDTO createPreference(PaymentDTO dto) {
        // Validación básica
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new PaymentException("El título del pago es obligatorio.");
        }
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new PaymentException("La cantidad debe ser mayor a cero.");
        }
        if (dto.getUnitPrice() == null || dto.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("El precio debe ser mayor a cero.");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new PaymentException("El correo electrónico es obligatorio.");
        }

        try {
            // Configurar el SDK
            MercadoPagoConfig.setAccessToken(accessToken);

            // Crear ítem
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(dto.getTitle())
                    .description(dto.getDescription())
                    .quantity(dto.getQuantity())
                    .unitPrice(dto.getUnitPrice()) // BigDecimal directo
                    .currencyId("PEN")
                    .build();

            // Crear payer
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

            // Guardar en BD
            Payment payment = paymentMapper.toEntity(dto);
            payment.setPreferenceId(preference.getId());
            payment.setStatus("created");

            Payment saved = paymentRepository.save(payment);

            // Devolver DTO incluyendo el initPoint (checkout URL)
            return paymentMapper.toDTO(saved, preference.getInitPoint());

        } catch (Exception e) {
            throw new PaymentException("Error al crear preferencia en Mercado Pago: " + e.getMessage());
        }
    }
}
