package br.com.malotes.service;
import br.com.malotes.domain.Descricao;
import br.com.malotes.domain.Funcionario;
import br.com.malotes.domain.Malote;
import br.com.malotes.repository.DescricaoRepository;
import br.com.malotes.repository.FuncionarioRepository;
import br.com.malotes.repository.MaloteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImportacaoMaloteService {

    private final FuncionarioRepository funcionarioRepository;
    private final DescricaoRepository descricaoRepository;
    private final MaloteRepository maloteRepository;

    public ImportacaoMaloteService(FuncionarioRepository funcionarioRepository,
                                   DescricaoRepository descricaoRepository,
                                   MaloteRepository maloteRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.descricaoRepository = descricaoRepository;
        this.maloteRepository = maloteRepository;
    }

    @Transactional
    public void importarDados() throws IOException {

        InputStream is = new ClassPathResource("Controle_Malotes.xlsx").getInputStream();

        try (is; Workbook workbook = new XSSFWorkbook(is)) {
            System.out.println("Lendo planilha...");

            for (Sheet sheet : workbook) {
                System.out.println("Aba: " + sheet.getSheetName());

                List<String> lstCells = new ArrayList<>();

                for (Row row : sheet) {

                    if (row.getRowNum() == 0) {
                        continue; // Pular cabeçalho
                    }

                    for (int coluna = 0; coluna < row.getLastCellNum(); coluna++) {

                        Cell cell = row.getCell(coluna, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                        String value = cell.toString();
                        lstCells.add(value);
                    }

                    System.out.println(lstCells);
                }
            }

        }
    }
}
