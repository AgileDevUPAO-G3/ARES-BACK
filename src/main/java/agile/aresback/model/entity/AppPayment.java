package agile.aresback.model.entity;

import agile.aresback.model.enums.StatusPago;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pagos")
public class AppPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private String email;

    @Column(name = "preference_id", unique = true, nullable = false)
    private String preferenceId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "external_reference", unique = true, length = 512)
    private String externalReference;

    @Column(name = "payment_id")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(name = "status_pago")
    private StatusPago statusPago;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.statusPago = StatusPago.CREADO;
    }
}
