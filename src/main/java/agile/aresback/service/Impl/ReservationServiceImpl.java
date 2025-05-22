package agile.aresback.service.Impl;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.exception.ReservationConflictException;
import agile.aresback.mapper.ReservationMapper;
import agile.aresback.model.entity.Client;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.enums.StateReservation;
import agile.aresback.repository.ReservationRepository;
import agile.aresback.service.ClientService;
import agile.aresback.service.MesaService;
import agile.aresback.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

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
        return reservationRepository.findByMesa(mesa); // Asegúrate de que este método exista en tu repositorio
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

        LocalDate fechaActual = LocalDate.now(); // ✅ separa fecha
        LocalTime horaActual = LocalTime.now();  // ✅ separa hora
        LocalTime horaFinCalculada = reservationDTO.getHoraInicio().plusHours(2);

        List<Reservation> conflictos = reservationRepository.findConflictingReservations(
                mesa.getId(),
                reservationDTO.getFechaReservada(),
                reservationDTO.getHoraInicio(),
                horaFinCalculada);

        if (!conflictos.isEmpty()) {
            throw new ReservationConflictException("Mesa reservada en la misma fecha y hora, intente otra fecha");
        }

        Reservation reservation = reservationMapper.toEntity(reservationDTO, client, mesa);
        reservation.setFechaRegistro(fechaActual);  // LocalDate
        reservation.setHoraFin(horaFinCalculada);   // LocalTime
        reservation.setStateReservation(StateReservation.EN_ESPERA);

        return createReservation(reservation);
    }

    @Override
    public List<Reservation> getReservationsByTimeRange(LocalDate startDate, LocalDate endDate) {
        return reservationRepository.findByFechaReservadaBetween(startDate, endDate);
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