package agile.aresback.api;

import agile.aresback.dto.ReservaDTO;
import agile.aresback.model.Reserva;
import agile.aresback.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public Reserva crearReserva(@RequestBody @Valid ReservaDTO reservaDTO) {
        return reservaService.crearReserva(reservaDTO);
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    @GetMapping("/{id}")
    public Reserva obtenerReserva(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    // Puedes agregar más endpoints si necesitas actualizar o eliminar reservas
}