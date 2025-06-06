package agile.aresback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationListDTO {

    @NotNull(message = "El ID de la reserva no puede ser nulo")
    private Integer id;

    @NotBlank(message = "El nombre completo es requerido")
    @Size(max = 100, message = "El nombre completo no puede exceder los 100 caracteres")
    private String nombreCliente;

    @NotNull(message = "El número de mesa es requerido")
    @Positive(message = "El número de mesa debe ser positivo")
    private Integer numeroMesa;

    @NotNull(message = "La cantidad de personas es requerida")
    @Positive(message = "La cantidad de personas debe ser positiva")
    private Integer capacidad;

    @NotBlank(message = "La zona es requerida")
    @Size(max = 50, message = "La zona no puede exceder los 50 caracteres")
    private String zone;

    @NotNull(message = "La fecha reservada es requerida")
    private LocalDate fechaReservada;

    @NotNull(message = "La hora de reserva es requerida")
    private LocalTime horaInicio;
}
