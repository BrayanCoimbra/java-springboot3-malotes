package br.com.malotes.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SUPMALOTE")
public class Malote {

    @Id
    @Column(name = "SEQCODMALOTE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CODFUNCIONARIO", nullable = false)
    private Funcionario funcionario;

    @Column(name = "DTENVIO", nullable = false)
    private LocalDate dataEnvio;

    @Column(name = "DTCONFERENCIA")
    private LocalDate dataConferencia;

    @Column(name = "SITUACAOMALOTE", nullable = false)
    private String situacaoMalote;

    @ManyToOne
    @JoinColumn(name = "CODDESCRICAO", nullable = false)
    private Descricao descricao;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public LocalDate getDataConferencia() {
        return dataConferencia;
    }

    public String getSituacaoMalote() {
        return situacaoMalote;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public void setDataConferencia(LocalDate dataConferencia) {
        this.dataConferencia = dataConferencia;
    }

    public void setSituacaoMalote(String situacaoMalote) {
        this.situacaoMalote = situacaoMalote;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }
}
