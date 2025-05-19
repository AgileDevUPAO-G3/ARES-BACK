package agile.aresback.api;

import agile.aresback.dto.MesaDto;
import agile.aresback.mapper.MesaMapper;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Zone;
import agile.aresback.service.MesaService;
import agile.aresback.service.ZoneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private MesaMapper mesaMapper;

    @GetMapping
    public ResponseEntity<List<MesaDto>> getAllMesaDto() {
        List<MesaDto> mesasDto = mesaService.getAllMesasDto();
        return ResponseEntity.ok(mesasDto);
    }

    @GetMapping("/{id}")
    public MesaDto getMesaById(@PathVariable Integer id) {
        Mesa mesa = mesaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        return mesaMapper.toDTO(mesa);
    }

    @PostMapping
    public MesaDto createMesa(@RequestBody MesaDto mesaDto) {
        Zone zone = zoneService.findById(mesaDto.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        Mesa mesa = mesaMapper.toEntity(mesaDto, zone);
        Mesa saved = mesaService.save(mesa);
        return mesaMapper.toDTO(saved);
    }

    @PutMapping("/{id}")
    public MesaDto updateMesa(@PathVariable Integer id, @RequestBody MesaDto mesaDto) {
        Zone zone = zoneService.findById(mesaDto.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        Mesa mesa = mesaMapper.toEntity(mesaDto, zone);
        mesa.setId(id);
        Mesa updated = mesaService.save(mesa);
        return mesaMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteMesa(@PathVariable Integer id) {
        mesaService.deleteById(id);
    }

}
