package agile.aresback.service;

import agile.aresback.dto.UserDTO;

public interface AuthService {
    UserDTO login(String username, String password);
}
