package agile.aresback.repository;

import agile.aresback.model.entity.Reservation;
import agile.aresback.model.enums.StateReservation;
import agile.aresback.model.enums.StateReservationClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import agile.aresback.model.entity.Mesa;

import java.time.LocalDate;
import java.time.LocalDateTime;
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


    List<Reservation> findAllByStateReservationAndCreatedAtBefore(StateReservation estado, LocalDateTime limite);

    List<Reservation> findAllByStateReservationClient(StateReservationClient stateReservationClient);

    @Query("""
    SELECT r FROM Reservation r
    WHERE 
        (:filtro IS NULL OR LOWER(CONCAT(r.client.nombre, ' ', r.client.apellido)) LIKE LOWER(CONCAT('%', :filtro, '%')) 
        OR LOWER(CONCAT(r.client.apellido, ' ', r.client.nombre)) LIKE LOWER(CONCAT('%', :filtro, '%'))
        OR r.client.dni LIKE CONCAT('%', :filtro, '%'))
    ORDER BY r.fechaReservada DESC, r.horaInicio DESC
""")
    List<Reservation> searchByNombreODni(@Param("filtro") String filtro);

}
