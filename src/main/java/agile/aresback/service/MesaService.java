package agile.aresback.service;

import agile.aresback.dto.MesaDto;
import agile.aresback.model.entity.Mesa;
import agile.aresback.model.enums.StateTable;

import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MesaService {
    List<MesaDto> getAllMesasDto();

    Mesa save(Mesa mesa);

    Optional<Mesa> findById(Integer id);

    void deleteById(Integer id);

    void cambiarEstado(Integer id, StateTable estado);
}
