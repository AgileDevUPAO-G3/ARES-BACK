package agile.aresback.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesaDto {
    private Integer id;
    private Integer capacidad;
    private String estado;
    private Integer numeroMesa;
    private String zoneName;
}