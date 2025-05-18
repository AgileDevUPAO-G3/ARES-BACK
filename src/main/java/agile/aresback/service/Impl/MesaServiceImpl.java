package agile.aresback.service.Impl;

import agile.aresback.dto.MesaDTO;
import agile.aresback.model.Mesa;
import agile.aresback.repository.MesaRepository;
import agile.aresback.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaServiceImpl implements MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Override
    public Mesa crearMesa(MesaDTO mesaDTO) {
        Mesa mesa = new Mesa(
                mesaDTO.getNumeroMesa(),
                mesaDTO.getCapacidad(),
                mesaDTO.getEstado());
        return mesaRepository.save(mesa);
    }

    @Override
    public Optional<Mesa> obtenerMesaPorId(Long id) {
        return mesaRepository.findById(id);
    }

    @Override
    public Optional<Mesa> obtenerMesaPorNumero(int numeroMesa) {
        return mesaRepository.findByNumeroMesa(numeroMesa);
    }

    @Override
    public List<Mesa> listarMesas() {
        return mesaRepository.findAll();
    }

    @Override
    public Mesa actualizarMesa(Long id, MesaDTO mesaDTO) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        mesa.setNumeroMesa(mesaDTO.getNumeroMesa());
        mesa.setCapacidad(mesaDTO.getCapacidad());
        mesa.setEstado(mesaDTO.getEstado());
        return mesaRepository.save(mesa);
    }

    @Override
    public void eliminarMesa(Long id) {
        mesaRepository.deleteById(id);
    }
}
