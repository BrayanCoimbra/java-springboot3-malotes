package br.com.malotes.controller;
import br.com.malotes.dto.ConsultaMaloteDTO;
import br.com.malotes.service.MaloteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/malotes")
public class MaloteController {

    private final MaloteService maloteService;

    public MaloteController(MaloteService maloteService) {
        this.maloteService = maloteService;
    }

    @GetMapping
    public Page<ConsultaMaloteDTO> listarMalotes(
            @RequestParam(required = false) Integer matricula,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataEnvio,
            @RequestParam(required = false) String descricao,
            Pageable pageable
    ) {
        return maloteService.consultaMalote(matricula, dataEnvio, descricao, pageable);
    }
}
