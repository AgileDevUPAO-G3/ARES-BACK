package agile.aresback.service.Impl;

import agile.aresback.model.entity.Reservation;
import agile.aresback.repository.ReservationRepository;
import agile.aresback.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return reservationRepository.findByFechaReservadaBetween(startTime, endTime);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> findById(Integer id) {
        return reservationRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        reservationRepository.deleteById(id);
    }
}