package agile.aresback.api;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.dto.ReservationDTO;
import agile.aresback.exception.PaymentException;
import agile.aresback.model.enums.StatusPago;
import agile.aresback.service.AppPaymentService;
import agile.aresback.service.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mercado-pago")
@RequiredArgsConstructor
public class AppPaymentController {

    private final AppPaymentService appPaymentService;
    private final ReservationService reservationService;
    private final ObjectMapper objectMapper;

    /**
     * Endpoint para crear una preferencia de pago
     */
    @PostMapping("/crear-preferencia")
    public ResponseEntity<AppPaymentDTO> crearPreferencia(@Valid @RequestBody AppPaymentDTO dto) {
        try {
            AppPaymentDTO respuesta = appPaymentService.createPreference(dto);
            return ResponseEntity.ok(respuesta);
        } catch (PaymentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Endpoint para procesar Webhook de Mercado Pago
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody Map<String, Object> payload) {
        try {
            String externalReference = (String) payload.get("external_reference");
            Long paymentId = Long.valueOf(payload.get("payment_id").toString());
            String status = (String) payload.get("status");

            if ("approved".equalsIgnoreCase(status)) {
                appPaymentService.linkReservationAfterPaymentApproved(externalReference, paymentId);
                return ResponseEntity.ok("Webhook procesado correctamente");
            } else {
                return ResponseEntity.ok("Pago no aprobado, no se crea reserva.");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar Webhook: " + e.getMessage());
        }
    }

    /**
     * Endpoint opcional para confirmar manualmente un pago aprobado
     */
    @PostMapping("/confirmar-pago")
    public ResponseEntity<String> confirmarPagoManual(@RequestParam String externalReference,
                                                      @RequestParam Long paymentId) {
        try {
            appPaymentService.linkReservationAfterPaymentApproved(externalReference, paymentId);
            return ResponseEntity.ok("Pago confirmado y reserva creada.");
        } catch (PaymentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
