package agile.aresback.api;

import agile.aresback.model.dto.BoletaDTO;
import agile.aresback.service.BoletaPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;git

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/boletas")
public class BoletaController {

    @Autowired
    private BoletaPdfService boletaPdfService;

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generarBoletaPdf(
            @RequestParam String cliente,
            @RequestParam String detalles,
            @RequestParam double total
    ) {
        BoletaDTO boleta = new BoletaDTO(cliente, detalles, total);
        ByteArrayInputStream bis = boletaPdfService.generarPdf(boleta);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=boleta.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }
}