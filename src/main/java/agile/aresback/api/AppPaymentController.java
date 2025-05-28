package agile.aresback.api;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.model.entity.Reservation;
import agile.aresback.repository.ReservationRepository;
import agile.aresback.service.AppPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercado-pago")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppPaymentController {

    private final AppPaymentService appPaymentService;
    private final ReservationRepository reservationRepository;

    @PostMapping("/crear-preferencia")
    public ResponseEntity<AppPaymentDTO> crearPreferencia(@RequestBody AppPaymentDTO dto) {
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada con ID: " + dto.getReservationId()));

        AppPaymentDTO result = appPaymentService.createPreference(dto, reservation);
        return ResponseEntity.ok(result);
    }
}
