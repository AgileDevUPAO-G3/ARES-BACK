package agile.aresback.dto;

import agile.aresback.model.entity.Zone;
import agile.aresback.model.enums.StateTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesaDto {
    private Integer numeroMesa;
    private Integer capacidad;
    private StateTable estado;
    private String zona;
}