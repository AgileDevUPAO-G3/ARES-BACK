package agile.aresback.api;

import agile.aresback.dto.ZoneDTO;
import agile.aresback.mapper.ZoneMapper;
import agile.aresback.model.entity.Zone;
import agile.aresback.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ZoneMapper zoneMapper;

    @GetMapping
    public List<ZoneDTO> getAllZones() {
        return zoneService.findAll()
                .stream()
                .map(zoneMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ZoneDTO getZoneById(@PathVariable Integer id) {
        Zone zone = zoneService.findById(id)
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));
        return zoneMapper.toDTO(zone);
    }

    @PostMapping
    public ZoneDTO createZone(@RequestBody ZoneDTO zoneDto) {
        Zone zone = zoneMapper.toEntity(zoneDto);
        Zone saved = zoneService.save(zone);
        return zoneMapper.toDTO(saved);
    }

    @PutMapping("/{id}")
    public ZoneDTO updateZone(@PathVariable Integer id, @RequestBody ZoneDTO zoneDto) {
        Zone zone = zoneMapper.toEntity(zoneDto);
        zone.setId(id);
        Zone updated = zoneService.save(zone);
        return zoneMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Integer id) {
        zoneService.deleteById(id);
    }
}
