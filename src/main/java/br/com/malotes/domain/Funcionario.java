package br.com.malotes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "SUPFUNCIONARIO")
public class Funcionario {
    @Id
    @Column(name = "SEQCODFUNCIONARIO")
    private Long id;

    @Column(name = "MATRICULA", nullable = false)
    private int matricula;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DTNASC", nullable = false)
    private LocalDate dataNascimento;

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
}
