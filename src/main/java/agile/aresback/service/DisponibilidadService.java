package agile.aresback.service;

import agile.aresback.dto.DisponibilidadDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DisponibilidadService {
    List<DisponibilidadDto> obtenerMesasDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin);
}
