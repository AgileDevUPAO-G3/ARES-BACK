package agile.aresback.service.Impl;

import agile.aresback.dto.ReservaDTO;
import agile.aresback.model.Cliente;
import agile.aresback.model.Mesa;
import agile.aresback.model.Reserva;
import agile.aresback.repository.ReservaRepository;
import agile.aresback.repository.MesaRepository;
import agile.aresback.service.ClienteService;
import agile.aresback.service.ReservaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteService clienteService;

    @Override
    public Reserva crearReserva(ReservaDTO reservaDTO) {
        // 1. Obtener o crear el cliente
        Cliente cliente = clienteService.buscarPorDniOCrear(
                reservaDTO.getCliente().getNombre(),
                reservaDTO.getCliente().getTelefono(),
                reservaDTO.getCliente().getEmail(),
                reservaDTO.getCliente().getDni());

        // 2. Buscar la mesa por su ID (puedes validar si existe)
        Mesa mesa = null;
        if (reservaDTO.getMesaId() != null) {
            mesa = mesaRepository.findById(reservaDTO.getMesaId())
                    .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        }

        // 3. Crear la entidad Reserva y setear los valores
        Reserva reserva = new Reserva();
        reserva.setCantidadPersonas(reservaDTO.getCantidadPersonas());
        reserva.setFechaHora(reservaDTO.getFechaHora());
        reserva.setEstado(reservaDTO.getEstado());
        reserva.setCliente(cliente);
        reserva.setMesa(mesa);

        // 4. Guardar la reserva
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }
}