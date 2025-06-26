package agile.aresback.mapper;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.dto.ReservationListDTO;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.entity.Client;
import agile.aresback.model.entity.Mesa;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) return null;

        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setFechaRegistro(reservation.getFechaRegistro());
        dto.setFechaReservada(reservation.getFechaReservada());
        dto.setHoraInicio(reservation.getHoraInicio());
        dto.setHoraFin(reservation.getHoraFin());

        dto.setStateReservation(reservation.getStateReservation());
        dto.setStateReservationClient(reservation.getStateReservationClient());
        dto.setMesaId(reservation.getMesa().getId());

        if (reservation.getClient() != null) {
            dto.setNombreCliente(reservation.getClient().getNombre());
            dto.setApellidoCliente(reservation.getClient().getApellido());
            dto.setEmailCliente(reservation.getClient().getEmail());
            dto.setTelefonoCliente(reservation.getClient().getTelefono());
            dto.setDniCliente(reservation.getClient().getDni());
        }

        return dto;
    }

    public Reservation toEntity(ReservationDTO dto, Client client, Mesa mesa) {
        if (dto == null) return null;

        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setFechaRegistro(null); // Se asigna en el servicio
        reservation.setFechaReservada(dto.getFechaReservada());
        reservation.setHoraInicio(dto.getHoraInicio());
        reservation.setHoraFin(dto.getHoraFin());

        reservation.setStateReservation(dto.getStateReservation() != null
                ? dto.getStateReservation()
                : agile.aresback.model.enums.StateReservation.PENDIENTE);

        reservation.setStateReservationClient(dto.getStateReservationClient() != null
                ? dto.getStateReservationClient()
                : agile.aresback.model.enums.StateReservationClient.EN_ESPERA);

        reservation.setClient(client);
        reservation.setMesa(mesa);

        return reservation;
    }

    public ReservationListDTO toListDTO(Reservation reservation) {
        if (reservation == null) return null;

        ReservationListDTO dto = new ReservationListDTO();
        dto.setId(reservation.getId());

        Client client = reservation.getClient();
        dto.setNombreCliente(client.getApellido() + " " + client.getNombre());
        dto.setDniCliente(client.getDni());

        dto.setNumeroMesa(reservation.getMesa().getNumeroMesa());
        dto.setCapacidad(reservation.getMesa().getCapacidad());
        dto.setZone(reservation.getMesa().getZone().getName());
        dto.setFechaReservada(reservation.getFechaReservada());
        dto.setHoraInicio(reservation.getHoraInicio());
        dto.setStateReservationClient(reservation.getStateReservationClient());

        return dto;
    }

}
