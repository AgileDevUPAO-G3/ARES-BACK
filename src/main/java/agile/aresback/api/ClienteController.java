package agile.aresback.api;

import agile.aresback.model.Cliente;
import agile.aresback.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{dni}")
    public Optional<Cliente> buscarPorDni(@PathVariable String dni) {
        return clienteService.buscarPorDni(dni);
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }
}
