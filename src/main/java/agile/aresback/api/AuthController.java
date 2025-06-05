package agile.aresback.api;

import agile.aresback.dto.LoginRequestDTO;
import agile.aresback.dto.UserDTO;
import agile.aresback.exception.BadRequestException;
import agile.aresback.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            UserDTO user = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario y/o contraseña incorrecto");
        }
    }
}