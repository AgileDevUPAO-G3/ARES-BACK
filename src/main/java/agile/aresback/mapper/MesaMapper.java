package agile.aresback.mapper;


import agile.aresback.dto.MesaDto;
import agile.aresback.model.entity.Mesa;

public class MesaMapper {

    public static MesaDto toDto(Mesa mesa) {
        return new MesaDto(
                mesa.getNumeroMesa(),
                mesa.getCapacidad(),
                mesa.getEstado(),
                mesa.getZone().getName()
        );
    }
}
