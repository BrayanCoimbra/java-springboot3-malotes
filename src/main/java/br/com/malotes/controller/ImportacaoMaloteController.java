package br.com.malotes.controller;
import br.com.malotes.service.ImportacaoMaloteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/importar")
public class ImportacaoMaloteController {

    @Autowired
    private ImportacaoMaloteService importacaoMaloteService;

    // Endpoint para iniciar a importação da planilha
    @PostMapping("/malotes")
    public String importarMalotes() throws IOException {
        try {
            importacaoMaloteService.importarDados();

        }catch (Exception e) {
            return "Erro ao realizar importação!" + e.getMessage();
        }
        return "Importação realizada com sucesso!";
    }
}
