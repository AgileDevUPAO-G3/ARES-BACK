package agile.aresback.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "zone")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(nullable = false)
    private Double price; // <- Corrige el nombre aquí

    @OneToMany(mappedBy = "zone")
    private List<Mesa> mesas;
}

