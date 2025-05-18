package agile.aresback.api;

import agile.aresback.dto.MesaDTO;
import agile.aresback.model.Mesa;
import agile.aresback.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @PostMapping
    public Mesa crearMesa(@RequestBody @Valid MesaDTO mesaDTO) {
        return mesaService.crearMesa(mesaDTO);
    }

    @GetMapping
    public List<Mesa> listarMesas() {
        return mesaService.listarMesas();
    }

    @GetMapping("/{id}")
    public Mesa obtenerMesa(@PathVariable Long id) {
        return mesaService.obtenerMesaPorId(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
    }
}
