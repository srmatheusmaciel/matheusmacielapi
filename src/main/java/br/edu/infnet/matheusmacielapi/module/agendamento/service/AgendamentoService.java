package br.edu.infnet.matheusmacielapi.module.agendamento.service;

import br.edu.infnet.matheusmacielapi.client.FeriadoApiClient;
import br.edu.infnet.matheusmacielapi.client.dto.VerificacaoFeriadoDTO;
import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.Agendamento;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.RecursoComum;
import br.edu.infnet.matheusmacielapi.module.agendamento.exception.AgendamentoException;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.AgendamentoRepository;
import br.edu.infnet.matheusmacielapi.service.MoradorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

import br.edu.infnet.matheusmacielapi.module.agendamento.repository.RecursoComumRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgendamentoService {

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoService.class);

    private final AgendamentoRepository agendamentoRepository;
    private final MoradorService moradorService;
    private final RecursoComumRepository recursoComumRepository;
    private final FeriadoApiClient feriadoApiClient;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            MoradorService moradorService,
            RecursoComumRepository recursoComumRepository,
            FeriadoApiClient feriadoApiClient) {
        this.agendamentoRepository = agendamentoRepository;
        this.moradorService = moradorService;
        this.recursoComumRepository = recursoComumRepository;
        this.feriadoApiClient = feriadoApiClient;
    }

    public Agendamento agendar(Long idMorador, Long idRecurso, LocalDate dataAgendamento) {
        validarDisponibilidade(idRecurso, dataAgendamento);

        verificarSeEhFeriado(dataAgendamento);

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

    @Transactional(readOnly = true)
    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado para o ID: " + id));
    }

    @Transactional(readOnly = true)
    public Collection<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    @Transactional
    public Agendamento atualizar(Long id, LocalDate novaData) {
        Agendamento agendamento = buscarPorId(id);

        if (novaData.equals(agendamento.getDataAgendamento())) {
            return agendamento;
        }

        validarDisponibilidade(agendamento.getRecurso().getId(), novaData);

        verificarSeEhFeriado(novaData);

        agendamento.setDataAgendamento(novaData);
        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void cancelar(Long id) {
        Agendamento agendamento = buscarPorId(id);
        agendamentoRepository.delete(agendamento);
    }

    private void verificarSeEhFeriado(LocalDate data) {
        try {
            logger.info("Verificando se a data {} é um feriado...", data);
            VerificacaoFeriadoDTO verificacao = feriadoApiClient.verificarFeriado(data);
            if (verificacao.isHoliday()) {
                logger.warn("ATENÇÃO: O agendamento está a ser feito num feriado: {}", verificacao.getHolidayName());
            } else {
                logger.info("A data {} não é um feriado.", data);
            }
        } catch (Exception e) {
            logger.error("Não foi possível verificar a data do feriado. O serviço pode estar offline.", e);
        }
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