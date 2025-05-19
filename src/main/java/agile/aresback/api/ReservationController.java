package agile.aresback.api;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.mapper.ReservationMapper;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import agile.aresback.service.ClientService;
import agile.aresback.service.MesaService;
import agile.aresback.service.ReservationService;
import agile.aresback.service.UserService;
import agile.aresback.model.entity.Client;
import agile.aresback.model.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private MesaService mesaService;

    @Autowired
    private UserService userService;

    // Endpoint para obtener las reservas de una mesa
    @GetMapping("/mesa/{mesaId}")
    public List<Reservation> getReservationsForMesa(@PathVariable Integer mesaId) {
        Mesa mesa = new Mesa(); // Aquí puedes cargar la mesa por ID desde la base de datos si es necesario
        mesa.setId(mesaId);
        return reservationService.getReservationsForMesa(mesa);
    }

    // Endpoint para crear una nueva reserva
    @PostMapping("/create")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    // Endpoint para obtener reservas dentro de un rango de tiempo
    @GetMapping("/byTimeRange")
    public List<Reservation> getReservationsByTimeRange(@RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return reservationService.getReservationsByTimeRange(startTime, endTime);
    }

    @GetMapping
    public List<ReservationDTO> getAllReservations() {
        return reservationService.findAll()
                .stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ReservationDTO getReservationById(@PathVariable Integer id) {
        Reservation reservation = reservationService.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        return reservationMapper.toDTO(reservation);
    }

    @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) {
        Client client = clientService.findById(reservationDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Mesa mesa = mesaService.findById(reservationDTO.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        User user = null;
        if (reservationDTO.getUserId() != null) {
            user = userService.findById(reservationDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }
        Reservation reservation = reservationMapper.toEntity(reservationDTO, client, mesa, user);
        Reservation saved = reservationService.createReservation(reservation);
        return reservationMapper.toDTO(saved);
    }

    @PutMapping("/{id}")
    public ReservationDTO updateReservation(@PathVariable Integer id, @RequestBody ReservationDTO reservationDTO) {
        Client client = clientService.findById(reservationDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Mesa mesa = mesaService.findById(reservationDTO.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        User user = null;
        if (reservationDTO.getUserId() != null) {
            user = userService.findById(reservationDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }
        Reservation reservation = reservationMapper.toEntity(reservationDTO, client, mesa, user);
        reservation.setId(id);
        Reservation updated = reservationService.createReservation(reservation);
        return reservationMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteById(id);
    }

}