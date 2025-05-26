package agile.aresback.api;

import agile.aresback.dto.PaymentDTO;
import agile.aresback.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercado-pago")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Puedes limitar esto a tu frontend en producción
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/crear-preferencia")
    public ResponseEntity<PaymentDTO> crearPreferencia(@RequestBody PaymentDTO dto) {
        PaymentDTO result = paymentService.createPreference(dto);
        return ResponseEntity.ok(result);
    }
}
