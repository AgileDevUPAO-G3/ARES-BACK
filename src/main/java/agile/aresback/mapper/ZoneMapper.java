package agile.aresback.mapper;

import agile.aresback.dto.ZoneDTO;
import agile.aresback.model.entity.Zone;
import org.springframework.stereotype.Component;

@Component
public class ZoneMapper {

    public ZoneDTO toDTO(Zone zone) {
        if (zone == null)
            return null;
        ZoneDTO dto = new ZoneDTO();
        dto.setId(zone.getId());
        dto.setName(zone.getName());
        return dto;
    }

    public Zone toEntity(ZoneDTO dto) {
        if (dto == null)
            return null;
        Zone zone = new Zone();
        zone.setId(dto.getId());
        zone.setName(dto.getName());
        return zone;
    }
}
