package agile.aresback.service.Impl;

import agile.aresback.model.entity.Zone;
import agile.aresback.repository.ZoneRepository;
import agile.aresback.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ZoneServiceImpl implements ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public Zone save(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }

    @Override
    public Optional<Zone> findById(Integer id) {
        return zoneRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        zoneRepository.deleteById(id);
    }
}