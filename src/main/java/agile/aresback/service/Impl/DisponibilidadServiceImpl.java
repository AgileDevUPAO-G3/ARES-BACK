package agile.aresback.service.Impl;

import agile.aresback.dto.DisponibilidadDto;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.enums.StateReservation;
import agile.aresback.repository.MesaRepository;
import agile.aresback.repository.ReservationRepository;
import agile.aresback.service.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisponibilidadServiceImpl implements DisponibilidadService {

    private final MesaRepository mesaRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<DisponibilidadDto> obtenerMesasDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        List<Mesa> todasLasMesas = mesaRepository.findAll();
        List<Reservation> reservas = reservationRepository.findByFechaHora(fecha.atStartOfDay(), horaInicio, horaFin);

        return todasLasMesas.stream().map(mesa -> {
            Optional<Reservation> reserva = reservas.stream()
                    .filter(r -> r.getMesa().getId().equals(mesa.getId()))
                    .findFirst();

            String estado;
            if (reserva.isPresent()) {
                StateReservation estadoReserva = reserva.get().getStateReservation();
                switch (estadoReserva) {
                    case EN_PROGRESO -> estado = "EN_PROGRESO";
                    case OCUPADA -> estado = "OCUPADA";
                    case EN_ESPERA -> estado = "EN_ESPERA";
                    case ASISTIO -> estado = "ASISTIO";
                    case NO_ASISTIO -> estado = "NO_ASISTIO";
                    default -> estado = "RESERVADA";
                }
            } else {
                estado = "DISPONIBLE";
            }

            return new DisponibilidadDto(
                    mesa.getNumeroMesa(),
                    mesa.getCapacidad(),
                    mesa.getZone().getName(),
                    estado
            );
        }).collect(Collectors.toList());
    }
}