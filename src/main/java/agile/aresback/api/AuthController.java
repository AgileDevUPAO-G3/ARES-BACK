package agile.aresback.api;

import agile.aresback.dto.LoginRequestDTO;
import agile.aresback.dto.UserDTO;
import agile.aresback.exception.BadRequestException;
import agile.aresback.model.entity.User;
import agile.aresback.service.AuthService;
import agile.aresback.config.JwtUtil;
import agile.aresback.dto.LoginResponseDTO;
import agile.aresback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /*
     * @PostMapping("/login")
     * public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
     * try {
     * UserDTO user = authService.login(loginRequest.getUsername(),
     * loginRequest.getPassword());
     * return ResponseEntity.ok(user);
     * } catch (BadRequestException e) {
     * return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
     * .body("Usuario y/o contraseña incorrecto");
     * }
     * }
     */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Usuario y/o contraseña incorrecto"));

        if (!request.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario y/o contraseña incorrecto");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(new LoginResponseDTO(true, user.getRole().name(), "Inicio de sesión exitoso", token));
    }
}