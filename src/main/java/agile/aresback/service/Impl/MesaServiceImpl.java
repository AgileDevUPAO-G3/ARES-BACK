package agile.aresback.service.Impl;


import agile.aresback.dto.MesaDto;
import agile.aresback.mapper.MesaMapper;
import agile.aresback.repository.MesaRepository;
import agile.aresback.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    @Override
    public List<MesaDto> getAllMesasDto() {
        return mesaRepository.findAll()
                .stream()
                .map(MesaMapper::toDto)
                .toList(); // o .collect(Collectors.toList()) si usas Java 8
    }

}
