package br.edu.infnet.matheusmacielapi.module.agendamento.repository;

import br.edu.infnet.matheusmacielapi.module.agendamento.domain.RecursoComum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoComumRepository extends JpaRepository<RecursoComum, Long> {
}