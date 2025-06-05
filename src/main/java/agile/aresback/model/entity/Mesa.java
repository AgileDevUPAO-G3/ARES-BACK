package agile.aresback.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import agile.aresback.model.enums.StateTable;

@Entity
@Table(name = "mesa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    private StateTable estado;

    @Column(name = "numero_mesa", nullable = false)
    private Integer numeroMesa;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @OneToMany(mappedBy = "mesa")
    private List<Reservation> reservations;

    @Column(nullable = false)
    private Double precio;

    private static final double PRECIO_POR_PERSONA = 5.0; // Ajustable

    @PrePersist
    @PreUpdate
    private void calcularPrecio() {
        double precioCapacidad = capacidad != null ? capacidad * PRECIO_POR_PERSONA : 0.0;
        double precioZona = (zone != null && zone.getPrice() != null) ? zone.getPrice() : 0.0;
        this.precio = precioCapacidad + precioZona;
    }
}
