package br.com.malotes.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SUPMALOTE")
public class malote {

    @Id
    @Column(name = "SEQCODMALOTE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CODFUNCIONARIO", nullable = false)
    private funcionario funcionario;

    @Column(name = "DTENVIO", nullable = false)
    private LocalDate dataEnvio;

    @Column(name = "DTCONFERENCIA")
    private LocalDate dataConferencia;

    @Column(name = "SITUACAOMALOTE", nullable = false)
    private String situacaoMalote;

    @ManyToOne
    @JoinColumn(name = "CODDESCRICAO", nullable = false)
    private descricao descricao;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public funcionario getFuncionario() {
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

    public descricao getDescricao() {
        return descricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFuncionario(funcionario funcionario) {
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

    public void setDescricao(descricao descricao) {
        this.descricao = descricao;
    }
}
