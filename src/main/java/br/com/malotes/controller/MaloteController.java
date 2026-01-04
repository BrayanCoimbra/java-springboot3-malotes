package br.com.malotes.controller;

import br.com.malotes.dto.ConsultaMaloteDTO;
import br.com.malotes.repository.MaloteRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/malotes")
public class MaloteController {
    private final MaloteRepository maloteRepository;

    private MaloteController(MaloteRepository maloteRepository){
        this.maloteRepository = maloteRepository;
    }

    @GetMapping
    public List<ConsultaMaloteDTO> listarMalotes(){
        return maloteRepository.listarConsultaMalotes();
    }

    @GetMapping("/filtro")
    public List<ConsultaMaloteDTO> filtrarMalotes(
            @RequestParam(required = false) Integer matricula,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataEnvio,
            @RequestParam(required = false) String descricao
    ) {
        return maloteRepository.filtrarConsultaMalotes(matricula, dataEnvio, descricao);
    }

}
