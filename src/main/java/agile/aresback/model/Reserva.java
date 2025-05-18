package agile.aresback.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Número de personas en la reserva
    @Column(nullable = false)
    private int cantidadPersonas;

    // Fecha y hora de la reserva
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // Estado de la reserva (pendiente, confirmada, cancelada, etc.)
    @Column(nullable = false)
    private String estado;

    // Relación con la mesa (opcional, si manejas mesas específicas)
    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    // Relación con el cliente (es obligatoria)
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Constructor vacío para JPA
    public Reserva() {
    }

    // Constructor con parámetros
    public Reserva(int cantidadPersonas, LocalDateTime fechaHora, String estado, Mesa mesa, Cliente cliente) {
        this.cantidadPersonas = cantidadPersonas;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.mesa = mesa;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}