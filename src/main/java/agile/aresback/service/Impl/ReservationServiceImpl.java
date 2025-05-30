package agile.aresback.service.Impl;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.dto.ReservationListDTO;
import agile.aresback.exception.ReservationConflictException;
import agile.aresback.mapper.ReservationMapper;
import agile.aresback.model.entity.Client;
import agile.aresback.model.entity.Mesa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.enums.StateReservation;
import agile.aresback.model.enums.StateReservationClient;
import agile.aresback.repository.ReservationRepository;
import agile.aresback.service.ClientService;
import agile.aresback.service.MesaService;
import agile.aresback.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MesaService mesaService;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public List<Reservation> getReservationsForMesa(Mesa mesa) {
        return reservationRepository.findByMesa(mesa);
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation createReservationWithClient(ReservationDTO reservationDTO) {
        Client client = clientService.findByDni(reservationDTO.getDniCliente())
                .orElseGet(() -> {
                    Client nuevoCliente = new Client();
                    nuevoCliente.setNombre(reservationDTO.getNombreCliente());
                    nuevoCliente.setApellido(reservationDTO.getApellidoCliente());
                    nuevoCliente.setEmail(reservationDTO.getEmailCliente());
                    nuevoCliente.setTelefono(reservationDTO.getTelefonoCliente());
                    nuevoCliente.setDni(reservationDTO.getDniCliente());
                    return clientService.save(nuevoCliente);
                });

        Mesa mesa = mesaService.findById(reservationDTO.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();
        LocalTime horaInicio = reservationDTO.getHoraInicio();
        LocalTime horaFinCalculada = horaInicio.plusHours(2);

        // Validación: si la reserva es para hoy, debe hacerse con al menos 5h de anticipación
        if (reservationDTO.getFechaReservada().isEqual(fechaActual)
                && horaInicio.isBefore(horaActual.plusHours(5))) {
            throw new ReservationConflictException("La reserva debe realizarse con al menos 5 horas de anticipación");
        }

        List<Reservation> conflictos = reservationRepository.findConflictingReservations(
                mesa.getId(),
                reservationDTO.getFechaReservada(),
                horaInicio,
                horaFinCalculada);

        if (!conflictos.isEmpty()) {
            throw new ReservationConflictException("Mesa reservada en la misma fecha y hora, intente otra fecha");
        }

        Reservation reservation = reservationMapper.toEntity(reservationDTO, client, mesa);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setFechaRegistro(fechaActual);
        reservation.setHoraFin(horaFinCalculada);
        reservation.setStateReservation(StateReservation.PENDIENTE); // Esperando pago
        reservation.setStateReservationClient(StateReservationClient.EN_ESPERA); // Cliente aún no ha llegado

        return createReservation(reservation);
    }

    @Override
    public List<Reservation> getReservationsByTimeRange(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findByFechaReservadaBetween(startDate, endDate);
    }

    @Override
    public List<ReservationListDTO> getAllReservationsForView() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toListDTO)
                .collect(Collectors.toList());
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

    @Override
    @Transactional
    public void deleteExpiredPendingReservations() {
        LocalDateTime limite = LocalDateTime.now().minusMinutes(10);
        int eliminadas = reservationRepository.deleteExpiredReservations(StateReservation.PENDIENTE, limite);
        if (eliminadas > 0) {
            log.info("Se eliminaron automáticamente {} reservas pendientes por superar los 10 minutos sin pago.", eliminadas);
        }
    }



}
