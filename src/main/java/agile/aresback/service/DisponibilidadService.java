package agile.aresback.service;

import agile.aresback.dto.MesaDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DisponibilidadService {
    List<MesaDto> buscarMesasDisponibles(LocalDate fecha, LocalTime hora);
}