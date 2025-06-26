package agile.aresback.service.Impl;

import agile.aresback.dto.MesaDto;
import agile.aresback.mapper.MesaMapper;
import agile.aresback.repository.MesaRepository;
import agile.aresback.service.MesaService;
import lombok.RequiredArgsConstructor;
import agile.aresback.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.enums.StateTable;

import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    @Autowired
    private MesaMapper mesaMapper;

    @Override
    public List<MesaDto> getAllMesasDto() {
        return mesaRepository.findAll()
                .stream()
                .map(mesaMapper::toDTO)
                .toList(); // o .collect(Collectors.toList()) si usas Java 8
    }

    @Override
    public Mesa save(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    @Override
    public Optional<Mesa> findById(Integer id) {
        return mesaRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        mesaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void cambiarEstado(Integer id, StateTable nuevoEstado) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id " + id));
        System.out.println("---- MESA ANTES DE CAMBIAR ESTADO ----");
        System.out.println("ID: " + mesa.getId());
        System.out.println("Estado: " + mesa.getEstado());
        mesa.setEstado(nuevoEstado);
        System.out.println("---- MESA ANTES DE GUARDAR ----");
        System.out.println("ID: " + mesa.getId());
        System.out.println("Capacidad: " + mesa.getCapacidad());
        System.out.println("Estado: " + mesa.getEstado());
        System.out.println("Número de mesa: " + mesa.getNumeroMesa());
        System.out.println("Zona: " + (mesa.getZone() != null ? mesa.getZone().getId() : "null"));
        System.out.println("--------------------------------");
        mesaRepository.save(mesa);
    }

}
