package agile.aresback.api;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import agile.aresback.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Endpoint para obtener las reservas de una mesa
   /* @GetMapping("/mesa/{mesaId}")
    public List<Reservation> getReservationsForMesa(@PathVariable Integer mesaId) {
        Mesa mesa = new Mesa();  // Aquí puedes cargar la mesa por ID desde la base de datos si es necesario
        mesa.setId(mesaId);
        return reservationService.getReservationsForMesa(mesa);
    }*/

    // Endpoint para crear una nueva reserva
    @PostMapping("/create")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    // Endpoint para obtener reservas dentro de un rango de tiempo
    @GetMapping("/byTimeRange")
    public List<Reservation> getReservationsByTimeRange(@RequestParam LocalDateTime startTime,
                                                        @RequestParam LocalDateTime endTime) {
        return reservationService.getReservationsByTimeRange(startTime, endTime);
    }
}