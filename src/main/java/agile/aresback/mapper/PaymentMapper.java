package agile.aresback.mapper;

import agile.aresback.dto.PaymentDTO;
import agile.aresback.model.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDTO toDTO(Payment payment, String initPoint) {
        if (payment == null) return null;

        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setTitle(payment.getTitle());
        dto.setDescription(payment.getDescription());
        dto.setQuantity(payment.getQuantity());
        dto.setUnitPrice(payment.getUnitPrice());
        dto.setEmail(payment.getEmail());
        dto.setPreferenceId(payment.getPreferenceId());
        dto.setStatus(payment.getStatus());
        dto.setCreatedAt(payment.getCreatedAt());
        dto.setInitPoint(initPoint); // ✅ Setea el campo de redirección

        return dto;
    }

    public Payment toEntity(PaymentDTO dto) {
        if (dto == null) return null;

        Payment payment = new Payment();
        payment.setTitle(dto.getTitle());
        payment.setDescription(dto.getDescription());
        payment.setQuantity(dto.getQuantity());
        payment.setUnitPrice(dto.getUnitPrice());
        payment.setEmail(dto.getEmail());
        return payment;
    }
}
