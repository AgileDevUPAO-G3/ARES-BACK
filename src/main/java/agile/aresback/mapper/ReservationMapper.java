package agile.aresback.mapper;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.dto.ReservationListDTO;
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

        // ✅ Ya es LocalDate directamente
        dto.setFechaRegistro(reservation.getFechaRegistro());

        dto.setFechaReservada(reservation.getFechaReservada());
        dto.setHoraInicio(reservation.getHoraInicio());
        dto.setHoraFin(reservation.getHoraFin());

        dto.setStateReservation(reservation.getStateReservation() != null
                ? reservation.getStateReservation().name()
                : null);

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

        // Se asigna en el servicio
        reservation.setFechaRegistro(null);

        reservation.setFechaReservada(dto.getFechaReservada());
        reservation.setHoraInicio(dto.getHoraInicio());
        reservation.setHoraFin(dto.getHoraFin());

        if (dto.getStateReservation() != null) {
            reservation.setStateReservation(StateReservation.valueOf(dto.getStateReservation()));
        } else {
            reservation.setStateReservation(StateReservation.EN_ESPERA);
        }

        reservation.setClient(client);
        reservation.setMesa(mesa);

        return reservation;
    }

    // Nuevo método para mapear a ReservationListDTO
    public ReservationListDTO toListDTO(Reservation reservation) {
        if (reservation == null) return null;

        ReservationListDTO dto = new ReservationListDTO();

        dto.setId(reservation.getId());

        // Concatenar apellido + nombre
        String fullName = reservation.getClient().getApellido() + " " + reservation.getClient().getNombre();
        dto.setNombreCliente(fullName);

        dto.setNumeroMesa(reservation.getMesa().getNumeroMesa());
        dto.setCapacidad(reservation.getMesa().getCapacidad());
        dto.setZone(reservation.getMesa().getZone().getName());

        dto.setFechaReservada(reservation.getFechaReservada());
        dto.setHoraInicio(reservation.getHoraInicio());

        return dto;
    }

}