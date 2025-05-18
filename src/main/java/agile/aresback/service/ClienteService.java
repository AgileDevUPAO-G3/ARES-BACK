package agile.aresback.service;

import java.util.Optional;
import java.util.List;

import agile.aresback.model.Cliente;

public interface ClienteService {
    Cliente buscarPorDniOCrear(String nombre, String telefono, String email, String dni);

    Optional<Cliente> buscarPorDni(String dni);

    List<Cliente> listarClientes();
}