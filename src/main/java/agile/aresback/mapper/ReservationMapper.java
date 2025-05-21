package agile.aresback.mapper;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.entity.Client;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.User;
import agile.aresback.model.enums.StateReservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null)
            return null;
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setFechaRegistro(reservation.getFechaRegistro());
        dto.setFechaReservada(reservation.getFechaReservada());
        dto.setHoraInicio(reservation.getHoraInicio());
        dto.setHoraFin(reservation.getHoraFin());
        dto.setStateReservation(reservation.getStateReservation().name());
        dto.setClientId(reservation.getClient() != null ? reservation.getClient().getId() : null);
        dto.setMesaId(reservation.getMesa() != null ? reservation.getMesa().getId() : null);
        dto.setUserId(reservation.getUser() != null ? reservation.getUser().getId() : null);
        return dto;
    }

    public Reservation toEntity(ReservationDTO dto, Client client, Mesa mesa, User user) {
        if (dto == null)
            return null;
        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setFechaRegistro(dto.getFechaRegistro());
        reservation.setFechaReservada(dto.getFechaReservada());
        reservation.setHoraInicio(dto.getHoraInicio());
        reservation.setHoraFin(dto.getHoraFin());
        reservation.setStateReservation(StateReservation.valueOf(dto.getStateReservation()));
        reservation.setClient(client);
        reservation.setMesa(mesa);
        reservation.setUser(user);
        return reservation;
    }
}
