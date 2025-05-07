package agile.aresback.model.entity;

import agile.aresback.model.enums.StateTable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "capacidad", nullable = false, length = 10)
    private Integer capacidad;
    @Column(name ="numero_mesa", nullable = false)
    private Integer numeroMesa;
    @Enumerated(EnumType.STRING)
    private StateTable estado;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private List<Reservation> reservation;

}
