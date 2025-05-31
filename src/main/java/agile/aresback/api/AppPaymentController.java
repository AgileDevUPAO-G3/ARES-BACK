package agile.aresback.api;

import agile.aresback.dto.AppPaymentDTO;
import agile.aresback.exception.PaymentException;
import agile.aresback.model.enums.StatusPago;
import agile.aresback.service.AppPaymentService;
import agile.aresback.service.ReservationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
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
        log.info("[WEBHOOK] Recibido payload: {}", payload);

        try {
            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            if (data != null && data.get("id") != null) {
                String paymentIdStr = data.get("id").toString();
                appPaymentService.actualizarPagoDesdeWebhook(paymentIdStr);
                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.badRequest().body("Missing data.id in payload");
            }
        } catch (Exception e) {
            log.error("[WEBHOOK] Error al procesar el webhook: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error interno");
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
