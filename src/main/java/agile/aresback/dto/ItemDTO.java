package agile.aresback.dto;

import java.math.BigDecimal;

public class ItemDTO {
    private String nombre;
    private int cantidad;
    private BigDecimal precio;

    // Constructor
    public ItemDTO(String nombre, int cantidad, BigDecimal precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public BigDecimal getPrecio() { return precio; }

    // toString opcional
    @Override
    public String toString() {
        return cantidad + " x " + nombre + " - S/" + precio;
    }
}

