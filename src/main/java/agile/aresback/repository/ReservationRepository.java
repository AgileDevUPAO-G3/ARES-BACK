package agile.aresback.repository;

import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByMesa(Mesa mesa); // Obtener reservas de una mesa
    List<Reservation> findByFechaReservadaBetween(LocalDateTime startTime, LocalDateTime endTime); // Obtener reservas en un rango de tiempo
    @Query("SELECT r FROM Reservation r WHERE r.fechaReservada = :fecha " +
            "AND :horaInicio < r.horaFin AND :horaFin > r.horaInicio")
    List<Reservation> findByFechaHora(@Param("fecha") LocalDateTime fecha,
                                      @Param("horaInicio") LocalTime horaInicio,
                                      @Param("horaFin") LocalTime horaFin);

}
