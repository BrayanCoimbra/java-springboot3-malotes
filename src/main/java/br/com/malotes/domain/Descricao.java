package br.com.malotes.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "SUPDESCRICAO")
public class Descricao {

    @Id
    @Column(name = "SEQCODDESCRICAO")
    private Long id;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
