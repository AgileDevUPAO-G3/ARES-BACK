package agile.aresback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadDto {
    private Integer numeroMesa;
    private Integer capacidad;
    private String zona;
    private String estado;
}