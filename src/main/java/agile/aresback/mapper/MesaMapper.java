package agile.aresback.mapper;

import agile.aresback.dto.MesaDto;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Zone;
import agile.aresback.model.enums.StateTable;
import org.springframework.stereotype.Component;

@Component
public class MesaMapper {

    public MesaDto toDTO(Mesa mesa) {
        if (mesa == null)
            return null;
        MesaDto dto = new MesaDto();
        dto.setId(mesa.getId());
        dto.setCapacidad(mesa.getCapacidad());
        dto.setEstado(mesa.getEstado().name()); // Enum a String
        dto.setNumeroMesa(mesa.getNumeroMesa());
        dto.setZoneName(mesa.getZone() != null ? mesa.getZone().getName() : null);
        return dto;
    }

    public Mesa toEntity(MesaDto dto, Zone zone) {
        if (dto == null)
            return null;
        Mesa mesa = new Mesa();
        mesa.setId(dto.getId());
        mesa.setCapacidad(dto.getCapacidad());
        mesa.setEstado(StateTable.valueOf(dto.getEstado())); // String a Enum
        mesa.setNumeroMesa(dto.getNumeroMesa());
        mesa.setZone(zone);
        return mesa;
    }
}
