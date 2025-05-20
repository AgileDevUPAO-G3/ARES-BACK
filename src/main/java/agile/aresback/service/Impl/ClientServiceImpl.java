package agile.aresback.service.Impl;

import agile.aresback.model.entity.Client;
import agile.aresback.repository.ClientRepository;
import agile.aresback.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Optional<Client> findByDni(String dni) {
        return clientRepository.findByDni(dni);
    }
}
