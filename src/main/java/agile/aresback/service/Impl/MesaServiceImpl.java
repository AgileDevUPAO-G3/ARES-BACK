package agile.aresback.service.Impl;

import agile.aresback.dto.MesaDto;
import agile.aresback.mapper.MesaMapper;
import agile.aresback.repository.MesaRepository;
import agile.aresback.service.MesaService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import agile.aresback.model.entity.Mesa;
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

}
