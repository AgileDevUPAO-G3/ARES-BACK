package agile.aresback.api;

import agile.aresback.dto.DisponibilidadDto;
import agile.aresback.dto.MesaDto;
import agile.aresback.model.entity.Mesa;
import agile.aresback.service.DisponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @PostMapping
    public ResponseEntity<List<MesaDto>> consultarDisponibilidad(@RequestBody DisponibilidadDto dto) {
        List<MesaDto> disponibles = disponibilidadService.buscarMesasDisponibles(dto.getFecha(), dto.getHora());
        return ResponseEntity.ok(disponibles);
    }
}
