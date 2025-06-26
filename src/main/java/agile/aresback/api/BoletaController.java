package agile.aresback.api;

import agile.aresback.dto.ItemDTO;
import agile.aresback.service.BoletaService;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/boleta")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @GetMapping("/enviar-descargar")
    public ResponseEntity<ByteArrayResource> enviarYDescargarBoleta(
            @RequestParam String cliente,
            @RequestParam String correo) {
        try {
            List<ItemDTO> items = List.of(
                    new ItemDTO("Arroz Chaufa", 1, new BigDecimal("15.00")),
                    new ItemDTO("Jugo de Maracuyá", 2, new BigDecimal("5.00"))
            );
            BigDecimal total = new BigDecimal("25.00");

            // 1. Generar PDF
            File boletaPdf = boletaService.generarBoletaPdf(cliente, LocalDate.now(), items, total);

            // 2. Enviar PDF por correo
            boletaService.enviarBoletaPorCorreo(boletaPdf, correo);

            // 3. Leer PDF y devolver como descarga
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(boletaPdf.toPath()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=boleta.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (IOException | DocumentException e) {
            return ResponseEntity.internalServerError()
                    .body(null);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
