package agile.aresback.service.Impl;


import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import agile.aresback.repository.MesaRepository;
import agile.aresback.repository.ReservationRepository;
import agile.aresback.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private MesaRepository mesaRepository;

    @Override
    public List<Reservation> getReservationsForMesa(Mesa mesa) {
        return reservationRepository.findByMesa(mesa); // Obtener las reservas de una mesa
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation); // Crear una nueva reserva
    }

    @Override
    public List<Reservation> getReservationsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return reservationRepository.findByFechaReservadaBetween(startTime, endTime); // Obtener reservas dentro de un rango de tiempo
    }
}
