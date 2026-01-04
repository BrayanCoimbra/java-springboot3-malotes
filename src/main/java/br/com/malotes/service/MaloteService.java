package br.com.malotes.service;

import br.com.malotes.dto.ConsultaMaloteDTO;
import br.com.malotes.repository.MaloteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class MaloteService {
    private final MaloteRepository maloteRepository;

    public MaloteService(MaloteRepository maloteRepository) {
        this.maloteRepository = maloteRepository;
    }

    public Page<ConsultaMaloteDTO> consultaMalote(
            Integer matricula,
            LocalDate dataEnvio,
            String descricao,
            Pageable pageable
    ){
        boolean temFiltro =
                matricula != null ||
                dataEnvio != null ||
                (descricao != null && !descricao.isEmpty());

        if (temFiltro){
            return maloteRepository.filtrarConsultaMalotes(
                    matricula,
                    dataEnvio,
                    descricao,
                    pageable
            );
        }
        return maloteRepository.listarConsultaMalotes(pageable);
    }
}