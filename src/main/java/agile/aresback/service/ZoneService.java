package agile.aresback.service;

import agile.aresback.model.entity.Zone;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface ZoneService {
    Zone save(Zone zone);

    List<Zone> findAll();

    Optional<Zone> findById(Integer id);

    void deleteById(Integer id);
}
