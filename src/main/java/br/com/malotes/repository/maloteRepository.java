package br.com.malotes.repository;

import br.com.malotes.dto.ConsultaMaloteDTO;
import br.com.malotes.domain.malote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface maloteRepository extends JpaRepository<malote, Long> {

    @Query("""
        SELECT new br.com.malotes.dto.ConsultaMaloteDTO(
            f.nome,
            CAST(f.matricula AS string),
            m.dataEnvio,
            m.situacaoMalote,
            d.descricao
        )
        FROM malote m
        JOIN m.funcionario f
        JOIN m.descricao d
        """)
    List<ConsultaMaloteDTO> listarConsultaMalotes();
}
