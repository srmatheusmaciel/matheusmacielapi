package br.edu.infnet.matheusmacielapi.module.agendamento.service;

import br.edu.infnet.matheusmacielapi.client.FeriadoApiClient;
import br.edu.infnet.matheusmacielapi.client.dto.VerificacaoFeriadoDTO;
import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.RecursoComum;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.Agendamento;
import br.edu.infnet.matheusmacielapi.dto.AgendamentoRequestDTO;
import br.edu.infnet.matheusmacielapi.dto.AgendamentoResponseDTO;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.module.agendamento.exception.AgendamentoException;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.AgendamentoRepository;
import br.edu.infnet.matheusmacielapi.repository.MoradorRepository;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.RecursoComumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoService.class);

    private final AgendamentoRepository agendamentoRepository;
    private final MoradorRepository moradorRepository;
    private final RecursoComumRepository recursoComumRepository;
    private final FeriadoApiClient feriadoApiClient;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            MoradorRepository moradorRepository,
            RecursoComumRepository recursoComumRepository,
            FeriadoApiClient feriadoApiClient) {
        this.agendamentoRepository = agendamentoRepository;
        this.moradorRepository = moradorRepository;
        this.recursoComumRepository = recursoComumRepository;
        this.feriadoApiClient = feriadoApiClient;
    }

    @Transactional
    public AgendamentoResponseDTO agendar(AgendamentoRequestDTO agendamentoDTO) {
        validarDisponibilidade(agendamentoDTO.getIdRecurso(), agendamentoDTO.getDataAgendamento());

        Morador morador = moradorRepository.findById(agendamentoDTO.getIdMorador())
                .orElseThrow(() -> new ResourceNotFoundException("Morador não encontrado para o ID: " + agendamentoDTO.getIdMorador()));

        RecursoComum recurso = recursoComumRepository.findById(agendamentoDTO.getIdRecurso())
                .orElseThrow(() -> new ResourceNotFoundException("Recurso comum não encontrado para o ID: " + agendamentoDTO.getIdRecurso()));

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setMorador(morador);
        novoAgendamento.setRecurso(recurso);
        novoAgendamento.setDataAgendamento(agendamentoDTO.getDataAgendamento());
        novoAgendamento.setStatus("CONFIRMADO");

        novoAgendamento = agendamentoRepository.save(novoAgendamento);

        return toResponseDTO(novoAgendamento);
    }

    @Transactional(readOnly = true)
    public Collection<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = findAgendamentoById(id);
        return toResponseDTO(agendamento);
    }

    @Transactional
    public AgendamentoResponseDTO atualizarData(Long id, LocalDate novaData) {
        Agendamento agendamento = findAgendamentoById(id);

        if (novaData.equals(agendamento.getDataAgendamento())) {
            return toResponseDTO(agendamento);
        }

        validarDisponibilidade(agendamento.getRecurso().getId(), novaData);

        agendamento.setDataAgendamento(novaData);
        agendamento = agendamentoRepository.save(agendamento);
        return toResponseDTO(agendamento);
    }

    @Transactional
    public void cancelar(Long id) {
        Agendamento agendamento = findAgendamentoById(id);
        agendamentoRepository.delete(agendamento);
    }


    private Agendamento findAgendamentoById(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado para o ID: " + id));
    }

    private AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
        dto.setId(agendamento.getId());
        dto.setDataAgendamento(agendamento.getDataAgendamento());
        dto.setStatus(agendamento.getStatus());
        dto.setNomeMorador(agendamento.getMorador().getNome());
        dto.setNomeRecurso(agendamento.getRecurso().getNome());

        VerificacaoFeriadoDTO feriadoInfo = verificarFeriado(agendamento.getDataAgendamento());
        dto.setFeriado(feriadoInfo.isHoliday());
        dto.setNomeFeriado(feriadoInfo.getHolidayName());

        return dto;
    }

    private VerificacaoFeriadoDTO verificarFeriado(LocalDate data) {
        try {
            logger.info("Orquestrando dados com feriado-api para a data {}", data);
            return feriadoApiClient.verificarFeriado(data);
        } catch (Exception e) {
            logger.error("Não foi possível contactar o feriado-api. Retornando informação de feriado como 'false'.", e);
            VerificacaoFeriadoDTO fallback = new VerificacaoFeriadoDTO();
            fallback.setHoliday(false);
            return fallback;
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