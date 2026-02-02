package br.com.malotes.repository;

import br.com.malotes.domain.Descricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DescricaoRepository extends JpaRepository<Descricao, Long> {

    Optional<Descricao> findByDescricao(String descricao);

}