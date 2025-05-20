package agile.aresback.service;

import agile.aresback.model.entity.Client;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    Client save(Client client);

    List<Client> findAll();

    Optional<Client> findById(Integer id);

    void deleteById(Integer id);

    Optional<Client> findByDni(String dni);
}
