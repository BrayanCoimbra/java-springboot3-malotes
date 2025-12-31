package br.com.malotes.controller;

import br.com.malotes.dto.ConsultaMaloteDTO;
import br.com.malotes.repository.maloteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/malotes")
public class maloteController {
    private final maloteRepository maloteRepository;

    private maloteController(maloteRepository maloteRepository){
        this.maloteRepository = maloteRepository;
    }

    @GetMapping
    public List<ConsultaMaloteDTO> listarMalotes(){
        return maloteRepository.listarConsultaMalotes();
    }
}
