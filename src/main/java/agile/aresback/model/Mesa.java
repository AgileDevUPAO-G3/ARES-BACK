package agile.aresback.model;

import jakarta.persistence.*;

@Entity
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private int numeroMesa;

    @Column(nullable = false)
    private int capacidad;

    @Column(nullable = false)
    private String estado; // Ejemplo: 'libre', 'reservada', 'ocupada'

    // Opcional: relación con Zona si lo usas
    // @ManyToOne
    // @JoinColumn(name = "zona_id")
    // private Zona zona;

    public Mesa() {
    }

    public Mesa(int numeroMesa, int capacidad, String estado) {
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}