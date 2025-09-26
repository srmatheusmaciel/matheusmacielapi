package br.edu.infnet.matheusmacielapi.module.agendamento.repository;

import br.edu.infnet.matheusmacielapi.module.agendamento.domain.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByRecursoIdAndDataAgendamento(Long idRecurso, LocalDate dataAgendamento);
}