package agile.aresback.repository;

import agile.aresback.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import agile.aresback.model.entity.Mesa;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByMesa(Mesa mesa);

    List<Reservation> findByFechaReservadaBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Reservation r WHERE r.mesa.id = :mesaId AND r.fechaReservada = :fechaReservada " +
            "AND ((r.horaInicio < :horaFin) AND (r.horaFin > :horaInicio))")
    List<Reservation> findConflictingReservations(@Param("mesaId") Integer mesaId,
                                                  @Param("fechaReservada") LocalDate fechaReservada,
                                                  @Param("horaInicio") LocalTime horaInicio,
                                                  @Param("horaFin") LocalTime horaFin);
}
