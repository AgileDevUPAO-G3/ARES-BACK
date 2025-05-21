package agile.aresback.service;

import agile.aresback.model.entity.Reservation;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Reservation createReservation(Reservation reservation);
    List<Reservation> getReservationsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    // Métodos desde develop
    List<Reservation> findAll();
    Optional<Reservation> findById(Integer id);
    void deleteById(Integer id);
}