package agile.aresback.service;
import agile.aresback.dto.MesaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MesaService {
    List<MesaDto> getAllMesasDto();
}
