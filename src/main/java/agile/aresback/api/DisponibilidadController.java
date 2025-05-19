package agile.aresback.api;

import agile.aresback.dto.DisponibilidadDto;
import agile.aresback.service.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping
    public ResponseEntity<List<DisponibilidadDto>> obtenerDisponibles(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("horaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
            @RequestParam("horaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFin
    ) {
        List<DisponibilidadDto> disponibles = disponibilidadService.obtenerMesasDisponibles(fecha, horaInicio, horaFin);
        return ResponseEntity.ok(disponibles);
    }
}