package br.edu.infnet.matheusmacielapi.repository;

import br.edu.infnet.matheusmacielapi.domain.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    List<Ocorrencia> findByStatusAndDataBetween(String status, LocalDate dataInicio, LocalDate dataFim);
}
