package br.com.malotes.service;
import br.com.malotes.domain.Descricao;
import br.com.malotes.domain.Funcionario;
import br.com.malotes.domain.Malote;
import br.com.malotes.repository.DescricaoRepository;
import br.com.malotes.repository.FuncionarioRepository;
import br.com.malotes.repository.MaloteRepository;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

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

    public void importarDados() throws IOException {

        try (InputStream is = new ClassPathResource("Controle_Malotes.xlsx").getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            DataFormatter formatter = new DataFormatter();

            for (Sheet sheet : workbook) {

                System.out.println("Aba: " + sheet.getSheetName());

                for (Row row : sheet) {

                    if (IgnorarLinha(row)) continue;

                    switch (sheet.getSheetName()) {

                        case "Funcionarios":
                            importarFuncionario(row, formatter);
                            break;

                        case "Descricao Situacao":
                            importarDescricao(row, formatter);
                            break;

                        case "Malotes":
                            importarMalote(row, formatter);
                            break;
                    }
                }
            }
        }
    }

    private void importarFuncionario(Row row, DataFormatter formatter) {

        Integer intMatricula = Integer.parseInt(formatter.formatCellValue(row.getCell(1)).trim());
        String strNomeFuncionario = formatter.formatCellValue(row.getCell(2));
        //LocalDate locDatDataNascimento = row.getCell(3).getLocalDateTimeCellValue().toLocalDate();
        LocalDate locDatDataNascimento = null;
        Cell cellData = row.getCell(3);
        if (cellData != null && cellData.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cellData)) {
            locDatDataNascimento = cellData.getLocalDateTimeCellValue().toLocalDate();
        }

        Funcionario funcionario = funcionarioRepository
                .findByMatricula(intMatricula)
                .orElseGet(() -> {
                    Funcionario f = new Funcionario();
                    f.setMatricula(intMatricula);
                    return f;
                });

        funcionario.setMatricula(intMatricula);
        funcionario.setNome(strNomeFuncionario);
        funcionario.setDataNascimento(locDatDataNascimento);

        funcionarioRepository.save(funcionario);
    }

    private void importarDescricao(Row row, DataFormatter formatter) {

        Long intCodDescricao = Long.parseLong(formatter.formatCellValue(row.getCell(0)).trim());
        String strDescricao = formatter.formatCellValue(row.getCell(1));

        Descricao descricao = descricaoRepository
                .findById(intCodDescricao)
                .orElseGet(() -> new Descricao());

        descricao.setDescricao(strDescricao);
        descricaoRepository.save(descricao);

    }

    private void importarMalote(Row row, DataFormatter formatter) {

        Long intCodIdMalote = Long.parseLong(formatter.formatCellValue(row.getCell(0)).trim());//Codigo da Matricula do Funcionario
        Integer intMatricula = Integer.parseInt(formatter.formatCellValue(row.getCell(1)).trim()); //Codigo da Matricula do Funcionario

        LocalDate locDatDataEnvio = null;
        Cell c2 = row.getCell(2);
        if (c2 != null && c2.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(c2)) {
            locDatDataEnvio = c2.getLocalDateTimeCellValue().toLocalDate(); //Data de Envio do malote
        }

        LocalDate locDatDataConferencia = null;
        Cell c3 = row.getCell(3);
        if (c3 != null && c3.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(c3)) {
            locDatDataConferencia = c3.getLocalDateTimeCellValue().toLocalDate(); //Data de conferencia do malote
        }

        String strSituacaoMalote = formatter.formatCellValue(row.getCell(4)); //Codigo da Situacao do Malote

        String descStr = formatter.formatCellValue(row.getCell(5)).trim();
        Long intCodDescricao = descStr.isEmpty() ? null : Long.parseLong(descStr); //Codigo da Descricao da Situacao

        if (intCodDescricao == null) {
            System.out.println("Linha " + row.getRowNum() + " ignorada: Código da descrição vazio");
            intCodDescricao = 6L; //Situação desconhecida
            strSituacaoMalote = "Desconhecido";
            //return;
        }

        Funcionario funcionario = funcionarioRepository.findByMatricula(intMatricula).orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Descricao descricao = descricaoRepository.findById(intCodDescricao).orElseThrow(() -> new RuntimeException("Descrição não encontrada"));

        Malote malote = maloteRepository.findById(intCodIdMalote).orElseGet(() -> new Malote());

        if (locDatDataEnvio == null) {
            locDatDataEnvio = LocalDate.now();
        }

        if (locDatDataConferencia == null) {
            locDatDataConferencia = LocalDate.now();
        }

        if (intCodDescricao == null) {
            intCodDescricao = 6L; //Situação desconhecida
            strSituacaoMalote = "Desconhecido";
        }

        malote.setFuncionario(funcionario);
        malote.setDataEnvio(locDatDataEnvio);
        malote.setDataConferencia(locDatDataConferencia);
        malote.setSituacaoMalote(strSituacaoMalote);
        malote.setDescricao(descricao);

        if (locDatDataEnvio == null) {
            System.out.println("Linha " + row.getRowNum() + " ignorada: Data de envio vazia");
            return;
        }

        maloteRepository.save(malote);
    }

    private boolean IgnorarLinha(Row row) {

        // linha inexistente
        if (row == null) return true;

        // cabeçalho
        if (row.getRowNum() == 0) return true;

        // verifica se todas as células estão vazias
        for (int c = 0; c < row.getLastCellNum(); c++) {

            Cell cell = row.getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            if (cell == null) continue;

            switch (cell.getCellType()) {

                case STRING:
                    if (!cell.getStringCellValue().trim().isEmpty()) {
                        return false; // tem conteúdo
                    }
                    break;

                case NUMERIC:
                case BOOLEAN:
                case FORMULA:
                    return false; // tem conteúdo

                default:
                    break;
            }
        }

        return true; //linha vazia, nao deve ser processada
    }
}