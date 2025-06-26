package agile.aresback.dto;

import agile.aresback.model.enums.StateTable;

public class EstadoMesaDTO {
    private StateTable nuevoEstado;

    public StateTable getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(StateTable nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
