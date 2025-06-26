package agile.aresback.mapper;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.dto.ReservationDTO;
import agile.aresback.model.entity.AppPayment;
import agile.aresback.model.enums.StatusPago;
import agile.aresback.model.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppPaymentMapper {

    private final ReservationMapper reservationMapper;

    @Autowired
    public AppPaymentMapper(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    public AppPaymentDTO toDTO(AppPayment entity) {
        if (entity == null) return null;

        AppPaymentDTO dto = new AppPaymentDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setEmail(entity.getEmail());
        dto.setPreferenceId(entity.getPreferenceId());
        dto.setStatusPago(entity.getStatusPago());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setExternalReference(entity.getExternalReference());
        dto.setPaymentId(entity.getPaymentId());

        // ✅ Aquí va el valor correcto del reservationId
        if (entity.getReservation() != null) {
            dto.setReservationId(entity.getReservation().getId());
        }

        return dto;
    }


    public AppPayment toEntity(AppPaymentDTO dto) {
        if (dto == null) return null;

        AppPayment entity = new AppPayment();
        entity.setTitle(dto.getTitle());
        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setEmail(dto.getEmail());
        entity.setExternalReference(dto.getExternalReference());
        entity.setPaymentId(dto.getPaymentId());

        if (dto.getStatusPago() != null) {
            entity.setStatusPago(dto.getStatusPago());
        }

        return entity;
    }

    public AppPayment toEntityWithReservation(AppPaymentDTO dto, Reservation reservation) {
        AppPayment entity = toEntity(dto);
        entity.setReservation(reservation);
        return entity;
    }

}
