package agile.aresback.service;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    List<Reservation> getReservationsForMesa(Mesa mesa);

    Reservation createReservation(Reservation reservation);

    Reservation createReservationWithClient(ReservationDTO reservationDTO);

    List<Reservation> getReservationsByTimeRange(LocalDate startDate, LocalDate endDate);

    List<Reservation> findAll();

    Optional<Reservation> findById(Integer id);

    void deleteById(Integer id);
}