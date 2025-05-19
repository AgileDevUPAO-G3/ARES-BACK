package agile.aresback.service;

import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import java.util.Optional;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {

    List<Reservation> getReservationsForMesa(Mesa mesa); // Obtener reservas de una mesa

    Reservation createReservation(Reservation reservation); // Crear una nueva reserva

    List<Reservation> getReservationsByTimeRange(LocalDateTime startTime, LocalDateTime endTime); // Obtener reservas
                                                                                                  // dentro de un rango
                                                                                                  // de tiempo

    List<Reservation> findAll();

    Optional<Reservation> findById(Integer id);

    void deleteById(Integer id);
}