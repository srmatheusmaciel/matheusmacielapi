package br.edu.infnet.matheusmacielapi.module.agendamento.service;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.Agendamento;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.RecursoComum;
import br.edu.infnet.matheusmacielapi.module.agendamento.exception.AgendamentoException;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.AgendamentoRepository;
import br.edu.infnet.matheusmacielapi.service.MoradorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import br.edu.infnet.matheusmacielapi.module.agendamento.repository.RecursoComumRepository;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final MoradorService moradorService;
    private final RecursoComumRepository recursoComumRepository;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            MoradorService moradorService,
            RecursoComumRepository recursoComumRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.moradorService = moradorService;
        this.recursoComumRepository = recursoComumRepository;
    }

    public Agendamento agendar(Long idMorador, Long idRecurso, LocalDate dataAgendamento) {
        validarDisponibilidade(idRecurso, dataAgendamento);

        Morador morador = moradorService.buscarPorId(idMorador);

        RecursoComum recurso = recursoComumRepository.findById(idRecurso)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso comum não encontrado para o ID: " + idRecurso));

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setMorador(morador);
        novoAgendamento.setRecurso(recurso);
        novoAgendamento.setDataAgendamento(dataAgendamento);
        novoAgendamento.setStatus("CONFIRMADO");

        return agendamentoRepository.save(novoAgendamento);
    }

    private void validarDisponibilidade(Long idRecurso, LocalDate dataAgendamento) {
        if (dataAgendamento.isBefore(LocalDate.now())) {
            throw new AgendamentoException("Não é permitido fazer agendamentos para datas passadas.");
        }

        boolean jaAgendado = agendamentoRepository.existsByRecursoIdAndDataAgendamento(idRecurso, dataAgendamento);
        if (jaAgendado) {
            throw new AgendamentoException("Este recurso já está agendado para a data selecionada.");
        }
    }
}