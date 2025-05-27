package agile.aresback.api;

import agile.aresback.service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boleta")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarBoleta(@RequestParam String nombre,
                                               @RequestParam String correo,
                                               @RequestParam double monto) {
        try {
            boletaService.generarYEnviarBoleta(nombre, correo, monto);
            return ResponseEntity.ok("Boleta enviada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar boleta: " + e.getMessage());
        }
    }
}


