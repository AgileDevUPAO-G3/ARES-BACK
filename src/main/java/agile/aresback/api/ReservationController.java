package agile.aresback.api;

import agile.aresback.dto.ReservationDTO;
import agile.aresback.dto.ReservationListDTO;
import agile.aresback.exception.ResourceNotFoundException;
import agile.aresback.mapper.ReservationMapper;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import agile.aresback.service.ClientService;
import agile.aresback.service.MesaService;
import agile.aresback.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    private MesaService mesaService;

    @Autowired
    private ClientService clientService;

    // Obtener reservas para una mesa, validando existencia de la mesa
    @GetMapping("/mesa/{mesaId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsForMesa(@PathVariable Integer mesaId) {
        Mesa mesa = mesaService.findById(mesaId)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada"));
        List<ReservationDTO> reservasDTO = reservationService.getReservationsForMesa(mesa).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDTO);
    }

    // Crear reserva con DTO que incluye datos cliente, usando la lógica en el servicio
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        Reservation reservationSaved = reservationService.createReservationWithClient(reservationDTO);
        ReservationDTO responseDTO = reservationMapper.toDTO(reservationSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/byTimeRange")
    public ResponseEntity<List<ReservationDTO>> getReservationsByTimeRange(@RequestParam LocalDate startDate,
                                                                           @RequestParam LocalDate endDate) {
        List<ReservationDTO> reservasDTO = reservationService.getReservationsByTimeRange(startDate, endDate).stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDTO);
    }

    @GetMapping("/vista")
    public ResponseEntity<List<ReservationListDTO>> getReservationsForView() {
        List<ReservationListDTO> reservas = reservationService.getAllReservationsForView();
        return ResponseEntity.ok(reservas);
    }


    // Obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<ReservationDTO> reservasDTO = reservationService.findAll().stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDTO);
    }

    // Obtener reserva por id
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Integer id) {
        Reservation reservation = reservationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        return ResponseEntity.ok(reservationMapper.toDTO(reservation));
    }

    // Actualizar reserva por id, usando DTO sin User
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Integer id,
                                                            @RequestBody @Valid ReservationDTO reservationDTO) {
        var client = clientService.findByDni(reservationDTO.getDniCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        var mesa = mesaService.findById(reservationDTO.getMesaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada"));

        Reservation reservation = reservationMapper.toEntity(reservationDTO, client, mesa);
        reservation.setId(id);

        Reservation updated = reservationService.createReservation(reservation);
        return ResponseEntity.ok(reservationMapper.toDTO(updated));
    }

    // Eliminar reserva por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ReservationDTO>> filterReservations(
            @RequestParam(required = false) String nombreCliente,
            @RequestParam(required = false) String codigoReserva,
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) String horaInicio,
            @RequestParam(required = false) String horaFin
    ) {
        List<Reservation> filtered = reservationService.filterReservations(nombreCliente, codigoReserva, fecha, horaInicio, horaFin);
        List<ReservationDTO> result = filtered.stream()
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
