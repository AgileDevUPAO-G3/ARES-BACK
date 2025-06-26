package agile.aresback.api;

import agile.aresback.dto.DisponibilidadDto;
import agile.aresback.dto.MesaDto;
import agile.aresback.service.DisponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @GetMapping
    public ResponseEntity<List<MesaDto>> consultarDisponibilidadGet(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("hora") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
            @RequestParam(value = "capacidad", required = false) Integer capacidad
    ) {
        List<MesaDto> disponibles = disponibilidadService.buscarMesasDisponibles(fecha, hora, capacidad);
        return ResponseEntity.ok(disponibles);
    }


}
