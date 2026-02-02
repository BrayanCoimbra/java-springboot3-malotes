package br.com.malotes.dto;
import java.time.LocalDate;

public class ConsultaMaloteDTO {
    private String nomeFuncionario;
    private Integer matricula;
    private LocalDate dataEnvio;
    private String situacaoMalote;
    private String descricaoSituacao;

    public ConsultaMaloteDTO(String nomeFuncionario, Integer matricula, LocalDate dataEnvio, String situacaoMalote, String descricaoSituacao) {
        this.nomeFuncionario = nomeFuncionario;
        this.matricula = matricula;
        this.dataEnvio = dataEnvio;
        this.situacaoMalote = situacaoMalote;
        this.descricaoSituacao = descricaoSituacao;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public String getSituacaoMalote() {
        return situacaoMalote;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public String getDescricaoSituacao() {
        return descricaoSituacao;
    }
}
