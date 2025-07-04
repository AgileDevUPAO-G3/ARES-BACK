package agile.aresback.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class DisponibilidadDto {
    private LocalDate fecha;
    private LocalTime hora;
    private Integer capacidadSolicitada;
}