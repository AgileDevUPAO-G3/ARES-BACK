package agile.aresback.mapper;

import agile.aresback.dto.ClientDTO;
import agile.aresback.model.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        if (client == null)
            return null;
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNombre(client.getNombre());
        dto.setApellido(client.getApellido());
        dto.setEmail(client.getEmail());
        dto.setTelefono(client.getTelefono());
        dto.setDni(client.getDni());
        return dto;
    }

    public Client toEntity(ClientDTO dto) {
        if (dto == null)
            return null;
        Client client = new Client();
        client.setId(dto.getId());
        client.setNombre(dto.getNombre());
        client.setApellido(dto.getApellido());
        client.setEmail(dto.getEmail());
        client.setTelefono(dto.getTelefono());
        client.setDni(dto.getDni());
        return client;
    }
}
