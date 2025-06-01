package agile.aresback.api;

import agile.aresback.dto.ClientDTO;
import agile.aresback.exception.ResourceNotFoundException;
import agile.aresback.mapper.ClientMapper;
import agile.aresback.model.entity.Client;
import agile.aresback.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Integer id) {
        Client client = clientService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return ResponseEntity.ok(clientMapper.toDTO(client));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client saved = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Integer id, @RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        client.setId(id);
        Client updated = clientService.save(client);
        return ResponseEntity.ok(clientMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byDni/{dni}")
    public ResponseEntity<ClientDTO> getClientByDni(@PathVariable String dni) {
        Client client = clientService.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con DNI: " + dni));
        return ResponseEntity.ok(clientMapper.toDTO(client));
    }
}
