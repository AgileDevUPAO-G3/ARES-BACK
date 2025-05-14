package agile.aresback.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;


@Entity
@Data
@Table(name = "cliente")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dni_cliente", nullable = false, unique = true)
    private String dniCliente; // El DNI como identificador único del cliente

    @Column(name = "nombre_cliente", nullable = false)
    private String clienteNombre;

    @Column(name = "apellido_cliente", nullable = false)
    private String clienteApellido;

    @Column(name = "email_cliente", nullable = false)
    private String clienteEmail;

    @Column(name = "telefono_cliente", nullable = false)
    private String clienteTelefono;

    // Relación con reservas
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Reservation> reservas;
}
