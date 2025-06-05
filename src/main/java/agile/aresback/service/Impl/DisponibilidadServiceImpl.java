package agile.aresback.service.Impl;

import agile.aresback.dto.MesaDto;
import agile.aresback.mapper.MesaMapper;
import agile.aresback.model.entity.Reservation;
import agile.aresback.model.enums.StateReservation;
import agile.aresback.model.enums.StateTable;
import agile.aresback.repository.MesaRepository;
import agile.aresback.repository.ReservationRepository;
import agile.aresback.service.DisponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DisponibilidadServiceImpl implements DisponibilidadService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private MesaMapper mesaMapper;

    @Override
    public List<MesaDto> buscarMesasDisponibles(LocalDate fecha, LocalTime hora) {
        LocalTime horaFin = hora.plusHours(2);

        List<Reservation> reservas = reservationRepository.findAll();

        return mesaRepository.findAll().stream()
                .map(mesa -> {
                    boolean ocupada = reservas.stream().anyMatch(r ->
                            r.getMesa().getId().equals(mesa.getId()) &&
                                    r.getFechaReservada().equals(fecha) &&
                                    hora.isBefore(r.getHoraFin()) &&
                                    horaFin.isAfter(r.getHoraInicio()) &&
                                    (
                                            r.getStateReservation() == StateReservation.PENDIENTE ||
                                                    r.getStateReservation() == StateReservation.RESERVADA
                                    )
                    );

                    // Mapea usando el mapper que ya tiene el precio incluido
                    MesaDto dto = mesaMapper.toDTO(mesa);
                    dto.setEstado(ocupada ? StateTable.RESERVADO.name() : StateTable.DISPONIBLE.name());

                    return dto;
                })
                .toList();
    }
}