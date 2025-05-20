package agile.aresback.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import agile.aresback.model.enums.StateTable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mesa")
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
}
