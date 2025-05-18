package agile.aresback.service;

import agile.aresback.dto.MesaDTO;
import agile.aresback.model.Mesa;

import java.util.List;
import java.util.Optional;

public interface MesaService {
    Mesa crearMesa(MesaDTO mesaDTO);

    Optional<Mesa> obtenerMesaPorId(Long id);

    Optional<Mesa> obtenerMesaPorNumero(int numeroMesa);

    List<Mesa> listarMesas();

    Mesa actualizarMesa(Long id, MesaDTO mesaDTO);

    void eliminarMesa(Long id);
}
