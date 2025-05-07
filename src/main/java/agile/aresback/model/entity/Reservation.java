package agile.aresback.model.entity;
import agile.aresback.model.enums.StateReservation;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha_reservada", nullable = false)
    private LocalDateTime fechaReservada;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    private StateReservation stateReservation;

    // Datos del cliente directamente en la reserva
    @Column(name = "nombre_cliente", nullable = false)
    private String clienteNombre;

    @Column(name = "apellido_cliente", nullable = false)
    private String clienteApellido;

    @Column(name = "email_cliente", nullable = false)
    private String clienteEmail;

    @Column(name = "telefono_cliente", nullable = false)
    private String clienteTelefono;


    //Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private Mesa mesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
