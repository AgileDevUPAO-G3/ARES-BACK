package agile.aresback.service.Impl;


import agile.aresback.model.entity.Mesa;
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
    public List<Mesa> getAllMesa() {
        return mesaRepository.findAll();
    }


}
