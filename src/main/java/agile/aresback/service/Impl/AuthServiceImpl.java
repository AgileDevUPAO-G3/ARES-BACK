package agile.aresback.service.Impl;

import agile.aresback.dto.UserDTO;
import agile.aresback.exception.BadRequestException;
import agile.aresback.mapper.UserMapper;
import agile.aresback.model.entity.User;
import agile.aresback.repository.UserRepository;
import agile.aresback.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new BadRequestException("Usuario y/o contraseña incorrecto"));

        // Solo permitimos login de administradores
        if (!user.getRole().name().equalsIgnoreCase("ADMIN")) {
            throw new BadRequestException("Acceso restringido al administrador");
        }

        return userMapper.toDTO(user);
    }
}
