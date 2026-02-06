package br.com.malotes.domain;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "SUPFUNCIONARIO")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQCODFUNCIONARIO")
    private Long id;

    @Column(name = "MATRICULA", nullable = false)
    private Integer matricula;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "DTNASC", nullable = false)
    private LocalDate dataNascimento;

    public Funcionario(String nome, Integer matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public Funcionario() {}

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public Integer getMatricula() {
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

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }
}
