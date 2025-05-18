package agile.aresback.service.Impl;

import agile.aresback.model.Cliente;
import agile.aresback.repository.ClienteRepository;
import agile.aresback.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente buscarPorDniOCrear(String nombre, String telefono, String email, String dni) {
        return clienteRepository.findByDni(dni)
                .orElseGet(() -> {
                    Cliente nuevo = new Cliente();
                    nuevo.setNombre(nombre);
                    nuevo.setTelefono(telefono);
                    nuevo.setEmail(email);
                    nuevo.setDni(dni);
                    return clienteRepository.save(nuevo);
                });
    }

    @Override
    public Optional<Cliente> buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}