package agile.aresback.model.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name="Zone")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name" , length=10, unique=true, nullable=false)
    private String name;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL )
    private List<Mesa> mesa;

}
