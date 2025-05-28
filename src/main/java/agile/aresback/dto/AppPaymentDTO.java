package agile.aresback.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AppPaymentDTO {
    private Long id;
    private String title;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String email;
    private String preferenceId;
    private String status;
    private LocalDateTime createdAt;
    private String initPoint;
    private Integer reservationId;
}
