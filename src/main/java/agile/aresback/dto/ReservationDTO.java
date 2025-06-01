package agile.aresback.dto;

import agile.aresback.model.enums.StateReservation;
import agile.aresback.model.enums.StateReservationClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate fechaRegistro;

    @NotNull(message = "La fecha reservada es obligatoria")
    private LocalDate fechaReservada;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalTime horaFin;

    @NotNull(message = "El estado de la reserva es obligatorio")
    private StateReservation stateReservation = StateReservation.PENDIENTE;

    @NotNull(message = "El estado del cliente es obligatorio")
    private StateReservationClient stateReservationClient = StateReservationClient.EN_ESPERA;

    @NotNull(message = "El ID de la mesa es obligatorio")
    private Integer mesaId;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "El apellido del cliente es obligatorio")
    private String apellidoCliente;

    @Email(message = "El correo electrónico no es válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String emailCliente;

    @NotBlank(message = "El teléfono del cliente es obligatorio")
    private String telefonoCliente;

    @NotBlank(message = "El DNI del cliente es obligatorio")
    private String dniCliente;
}
