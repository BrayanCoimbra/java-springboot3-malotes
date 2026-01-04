package br.com.malotes.repository;

import br.com.malotes.dto.ConsultaMaloteDTO;
import br.com.malotes.domain.Malote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface MaloteRepository extends JpaRepository<Malote, Long> {

    @Query("""
            SELECT new br.com.malotes.dto.ConsultaMaloteDTO(
                f.nome,
                f.matricula,
                m.dataEnvio,
                m.situacaoMalote,
                d.descricao
            )
            FROM Malote m
            JOIN m.funcionario f
            JOIN m.descricao d
            """)
    List<ConsultaMaloteDTO> listarConsultaMalotes();

    @Query("""
            SELECT new br.com.malotes.dto.ConsultaMaloteDTO(
                f.nome,
                f.matricula,
                m.dataEnvio,
                m.situacaoMalote,
                d.descricao
            )
            FROM Malote m
            JOIN m.funcionario f
            JOIN m.descricao d
            WHERE (:matricula IS NULL OR f.matricula = :matricula)
              AND (:dataEnvio IS NULL OR m.dataEnvio = :dataEnvio)
              AND (:descricao IS NULL OR LOWER(d.descricao) LIKE LOWER(CONCAT('%', :descricao, '%')))
        """)
    List<ConsultaMaloteDTO> filtrarConsultaMalotes(@Param("matricula") Integer matricula, @Param("dataEnvio") LocalDate dataEnvio, @Param("descricao") String desricao);
}