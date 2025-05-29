package agile.aresback.dto;

import agile.aresback.model.enums.StatusPago;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AppPaymentDTO {

    private Long id;

    @NotBlank(message = "El título del pago es obligatorio")
    private String title;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que cero")
    private Integer quantity;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.1", inclusive = true, message = "El precio debe ser mayor que 0")
    private BigDecimal unitPrice;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ingresar un correo válido")
    private String email;

    private String preferenceId;

    private StatusPago statusPago;

    private LocalDateTime createdAt;

    private String externalReference;

    private Long paymentId;

    @Valid
    private ReservationDTO reservationDTO;
}
