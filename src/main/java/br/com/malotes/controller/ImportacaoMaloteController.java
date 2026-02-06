package br.com.malotes.controller;
import br.com.malotes.service.ImportacaoMaloteService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/importarMalotes")
public class ImportacaoMaloteController {

    private final ImportacaoMaloteService service;

    public ImportacaoMaloteController(ImportacaoMaloteService service) {
        this.service = service;
    }

    // Endpoint para iniciar a importação da planilha
    @PostMapping("/malotes")
    public String importarMalotes() throws IOException {
        try {
            service.importarDados();

        }catch (Exception e) {
            return "Erro ao realizar importação!" + e.getMessage();
        }
        return "Importação realizada com sucesso!";
    }
}
