package agile.aresback.api;

import agile.aresback.dto.ClientDTO;
import agile.aresback.mapper.ClientMapper;
import agile.aresback.model.entity.Client;
import agile.aresback.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return clientService.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Integer id) {
        Client client = clientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return clientMapper.toDTO(client);
    }

    @PostMapping
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client saved = clientService.save(client);
        return clientMapper.toDTO(saved);
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable Integer id, @RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        client.setId(id);
        Client updated = clientService.save(client);
        return clientMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Integer id) {
        clientService.deleteById(id);
    }
}
