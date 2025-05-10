package agile.aresback.repository;

import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByMesa(Mesa mesa); // Obtener reservas de una mesa
    List<Reservation> findByFechaReservadaBetween(LocalDateTime startTime, LocalDateTime endTime); // Obtener reservas en un rango de tiempo
}
