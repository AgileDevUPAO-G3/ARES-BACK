package agile.aresback.repository;

import agile.aresback.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByFechaReservadaBetween(LocalDateTime inicio, LocalDateTime fin);
}
