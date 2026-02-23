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
import java.time.format.DateTimeFormatter;

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
             Workbook planilhaControleMalotes = new XSSFWorkbook(is)) {

            DataFormatter formatter = new DataFormatter();

            for (Sheet aba : planilhaControleMalotes) {

                String abaAtual = aba.getSheetName();

                System.out.println("Aba: " + abaAtual);

                for (Row linha : aba) {

                    if (IgnorarLinha(linha)) continue;

                    switch (abaAtual) {

                        case "Funcionarios":
                            importarFuncionario(linha, formatter);
                            break;

                        case "Descricao Situacao":
                            importarDescricao(linha, formatter);
                            break;

                        case "Malotes":
                            importarMalote(linha, formatter);
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

        //Codigo ID do registro
        Long lngCodIdMalote = null;
        Cell cellCodIdMalote = row.getCell(0);

        if (cellCodIdMalote != null && cellCodIdMalote.getCellType() == CellType.NUMERIC) {
            lngCodIdMalote = Long.parseLong(formatter.formatCellValue(row.getCell(0)).trim());
        }

        //Codigo da Matricula do Funcionario
        Integer intMatricula = null;
        Cell cellMatricula  = row.getCell(1);

        if (cellMatricula != null && cellMatricula.getCellType() == CellType.NUMERIC) {
            intMatricula = Integer.parseInt(formatter.formatCellValue(row.getCell(1)).trim());
        }

        //Data de Envio do Malote
        LocalDate locDatDataEnvio = LocalDate.now();
        Cell cellDataDeEnvioDoMalote = row.getCell(2);

        if (cellDataDeEnvioDoMalote.getCellType() == CellType.NUMERIC) {
            locDatDataEnvio = cellDataDeEnvioDoMalote
                    .getLocalDateTimeCellValue()
                    .toLocalDate();
        }

        //Data de Conferencia do Malote
        LocalDate locDatDataConferencia = LocalDate.now();
        Cell cellDataDeConferenciaDoMalote = row.getCell(3);

        if (cellDataDeConferenciaDoMalote.getCellType() == CellType.NUMERIC) {
            locDatDataConferencia = cellDataDeConferenciaDoMalote
                    .getLocalDateTimeCellValue()
                    .toLocalDate();
        }

        //Situacao do Malote
        String strSituacaoMalote = formatter.formatCellValue(row.getCell(4));

        //Descricao da situacao do malote
        String strDesc = formatter.formatCellValue(row.getCell(5)).trim();

        //Codigo da Descricao da Situacao
        Long lngCodDescricao = strDesc.isEmpty() ? null : Long.parseLong(strDesc);

        if (lngCodDescricao == null) {
            //Posteriormente deve-se tratar a excpetion corretamente
            lngCodDescricao = 6L; //Situação desconhecida
            strSituacaoMalote = "Desconhecido";
            System.out.println("Linha " + row.getRowNum() + " ignorada: Código da descrição vazio. Situação do malote: " + strSituacaoMalote);
        }

        //Para importar um malote, um funcionario e uma descriçao devem existir
        //caso contrário, a importação do malote é ignorada

        Funcionario funcionario = funcionarioRepository
                .findByMatricula(intMatricula)
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Descricao descricao = descricaoRepository
                .findById(lngCodDescricao)
                .orElseGet(() -> descricaoRepository.findById(6L) //Descrição para situação desconhecida
                        .orElseThrow(() -> new RuntimeException("Nenhuma descricao encontrada")));

        //Se o codigo do malote ja existir, ele sera atualizado
        //caso contrario, um novo registro sera criado
        Malote malote = maloteRepository.findById(lngCodIdMalote).orElseGet(() -> new Malote());

        malote.setFuncionario(funcionario);
        malote.setDataEnvio(locDatDataEnvio);
        malote.setDataConferencia(locDatDataConferencia);
        malote.setSituacaoMalote(strSituacaoMalote);
        malote.setDescricao(descricao);

        maloteRepository.save(malote);
    }

    //Helper para verificar se a linha deve ser ignorada (linha vazia ou cabecalho iodentificador das informacoes)
    private boolean IgnorarLinha(Row row) {

        // linha inexistente
        if (row == null) return true;

        // cabecalho
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