package br.edu.infnet.matheusmacielapi.module.agendamento.service;


import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.Agendamento;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.RecursoComum;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.AgendamentoRepository;
import br.edu.infnet.matheusmacielapi.repository.MoradorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final MoradorRepository moradorRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, MoradorRepository moradorRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.moradorRepository = moradorRepository;
    }

    public Agendamento agendar(Long idMorador, Long idRecurso, LocalDate dataAgendamento) {
        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new RuntimeException("Morador não encontrado!"));

        RecursoComum recurso = new RecursoComum();
        recurso.setId(idRecurso);

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setMorador(morador);
        novoAgendamento.setRecurso(recurso);
        novoAgendamento.setDataAgendamento(dataAgendamento);
        novoAgendamento.setStatus("CONFIRMADO");

        return agendamentoRepository.save(novoAgendamento);
    }
}