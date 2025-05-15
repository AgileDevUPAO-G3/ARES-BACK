package agile.aresback.api;


import agile.aresback.dto.MesaDto;
import agile.aresback.model.entity.Mesa;
import agile.aresback.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public ResponseEntity<List<MesaDto>> getAllMesaDto() {
        List<MesaDto> mesasDto = mesaService.getAllMesasDto();
        return ResponseEntity.ok(mesasDto);
    }


}
