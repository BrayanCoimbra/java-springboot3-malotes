package br.com.malotes.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class ConsultaMaloteDTO {
    private String nomeFuncionario;
    private String matricula;
    private LocalDate dataEnvio;
    private String situacaoMalote;
    private String descricaoSituacao;

    public ConsultaMaloteDTO(String nomeFuncionario, String matricula, LocalDate dataEnvio, String situacaoMalote, String descricaoSituacao) {
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

    public String getMatricula() {
        return matricula;
    }

    public String getDescricaoSituacao() {
        return descricaoSituacao;
    }
}
