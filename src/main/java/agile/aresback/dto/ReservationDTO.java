package agile.aresback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer id;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaReservada;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String stateReservation;
    private Integer clientId;
    private Integer mesaId;
    private Integer userId;
}