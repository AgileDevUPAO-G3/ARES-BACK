package agile.aresback.repository;

import agile.aresback.model.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    @Query(
            "SELECT m FROM Mesa m WHERE m.id NOT IN (" +
            "SELECT r.mesa.id FROM Reservation r " +
            "WHERE r.fechaReservada = :fecha " +
            "AND :horaInicio < r.horaFin AND :horaFin > r.horaInicio)")
    List<Mesa> findAvailableMesas(@Param("fecha") LocalDateTime fecha,
                                  @Param("horaInicio") LocalTime horaInicio,
                                  @Param("horaFin") LocalTime horaFin);


}