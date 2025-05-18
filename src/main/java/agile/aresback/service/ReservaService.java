package agile.aresback.service;

import java.util.List;
import java.util.Optional;

import agile.aresback.dto.ReservaDTO;
import agile.aresback.model.Reserva;

public interface ReservaService {
    Reserva crearReserva(ReservaDTO reservaDTO);

    Optional<Reserva> obtenerReservaPorId(Long id);

    List<Reserva> listarReservas();
}