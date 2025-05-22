package agile.aresback.dto;

public class BoletaDTO {
    private String cliente;
    private String detalles;
    private double total;

    // Constructor
    public BoletaDTO(String cliente, String detalles, double total) {
        this.cliente = cliente;
        this.detalles = detalles;
        this.total = total;
    }

    // Getters y Setters
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }}

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}