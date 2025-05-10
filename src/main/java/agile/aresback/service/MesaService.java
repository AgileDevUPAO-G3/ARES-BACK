package agile.aresback.service;
import agile.aresback.model.entity.Mesa;
import agile.aresback.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MesaService {
    List<Mesa> getAllMesa();
}
