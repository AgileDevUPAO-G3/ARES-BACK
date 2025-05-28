package agile.aresback.mapper;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.model.entity.AppPayment;
import org.springframework.stereotype.Component;

@Component
public class AppPaymentMapper {

    public AppPaymentDTO toDTO(AppPayment entity) {
        if (entity == null) return null;

        AppPaymentDTO dto = new AppPaymentDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setEmail(entity.getEmail());
        dto.setPreferenceId(entity.getPreferenceId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setInitPoint(null);
        dto.setReservationId(entity.getReservation() != null ? entity.getReservation().getId() : null);
        return dto;
    }

    public AppPaymentDTO toDTO(AppPayment entity, String initPoint) {
        AppPaymentDTO dto = toDTO(entity);
        if (dto != null) {
            dto.setInitPoint(initPoint);
        }
        return dto;
    }

    public AppPayment toEntity(AppPaymentDTO dto) {
        if (dto == null) return null;

        AppPayment entity = new AppPayment();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
