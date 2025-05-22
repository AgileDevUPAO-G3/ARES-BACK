package agile.aresback.mapper;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.entity.Client;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.enums.StateReservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    // Convierte entidad a DTO
    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) return null;

        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());

        // Fecha registro: LocalDateTime -> LocalDate (solo fecha)
        if (reservation.getFechaRegistro() != null) {
            dto.setFechaRegistro(reservation.getFechaRegistro().toLocalDate());
        }

        // Fecha reservada: LocalDate (igual que DTO)
        dto.setFechaReservada(reservation.getFechaReservada());

        // Hora inicio y fin (LocalTime igual en DTO)
        dto.setHoraInicio(reservation.getHoraInicio());
        dto.setHoraFin(reservation.getHoraFin());

        // Estado reserva a string
        dto.setStateReservation(reservation.getStateReservation() != null ? reservation.getStateReservation().name() : null);

        dto.setMesaId(reservation.getMesa() != null ? reservation.getMesa().getId() : null);

        if (reservation.getClient() != null) {
            dto.setNombreCliente(reservation.getClient().getNombre());
            dto.setApellidoCliente(reservation.getClient().getApellido());
            dto.setEmailCliente(reservation.getClient().getEmail());
            dto.setTelefonoCliente(reservation.getClient().getTelefono());
            dto.setDniCliente(reservation.getClient().getDni());
        } else {
            dto.setNombreCliente(null);
            dto.setApellidoCliente(null);
            dto.setEmailCliente(null);
            dto.setTelefonoCliente(null);
            dto.setDniCliente(null);
        }

        return dto;
    }

    // Convierte DTO a entidad
    public Reservation toEntity(ReservationDTO dto, Client client, Mesa mesa) {
        if (dto == null) return null;

        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());

        // fechaRegistro debe asignarse en el servicio (no desde DTO)
        reservation.setFechaRegistro(null);

        // fechaReservada: LocalDate es igual en DTO y entidad
        reservation.setFechaReservada(dto.getFechaReservada());

        // Hora inicio y fin
        reservation.setHoraInicio(dto.getHoraInicio());
        reservation.setHoraFin(dto.getHoraFin()); // o puede asignarse en servicio

        // Estado reserva
        if (dto.getStateReservation() != null) {
            reservation.setStateReservation(StateReservation.valueOf(dto.getStateReservation()));
        } else {
            reservation.setStateReservation(StateReservation.EN_ESPERA);
        }

        reservation.setClient(client);
        reservation.setMesa(mesa);

        return reservation;
    }
}
