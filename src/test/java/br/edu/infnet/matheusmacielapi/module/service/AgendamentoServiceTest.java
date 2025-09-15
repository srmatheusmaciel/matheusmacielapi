package br.edu.infnet.matheusmacielapi.module.service;

import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.Agendamento;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.RecursoComum;
import br.edu.infnet.matheusmacielapi.module.agendamento.exception.AgendamentoException;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.AgendamentoRepository;
import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.RecursoComumRepository;
import br.edu.infnet.matheusmacielapi.module.agendamento.service.AgendamentoService;
import br.edu.infnet.matheusmacielapi.service.MoradorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private MoradorService moradorService;

    @Mock
    private RecursoComumRepository recursoComumRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;


    @Test
    @DisplayName("Deve agendar com sucesso quando o recurso está disponível e a data é futura")
    void deveAgendarComSucesso_QuandoRecursoDisponivelEDataFutura() {
        Long idMorador = 1L;
        Long idRecurso = 1L;
        LocalDate dataAgendamento = LocalDate.now().plusDays(10);

        Morador moradorMock = new Morador();
        moradorMock.setId(idMorador);
        RecursoComum recursoMock = new RecursoComum(idRecurso, "Churrasqueira", null);
        Agendamento agendamentoSalvoMock = new Agendamento(1L, dataAgendamento, "CONFIRMADO", moradorMock, recursoMock);

        when(recursoComumRepository.findById(idRecurso)).thenReturn(Optional.of(recursoMock));

        when(moradorService.buscarPorId(idMorador)).thenReturn(moradorMock);

        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamentoSalvoMock);

        Agendamento agendamentoRealizado = agendamentoService.agendar(idMorador, idRecurso, dataAgendamento);

        assertNotNull(agendamentoRealizado);
        assertEquals("CONFIRMADO", agendamentoRealizado.getStatus());
        assertEquals(idMorador, agendamentoRealizado.getMorador().getId());
        assertEquals(idRecurso, agendamentoRealizado.getRecurso().getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao agendar recurso em data já reservada")
    void deveLancarExcecao_AoAgendarRecursoEmDataJaReservada() {
        Long idMorador = 1L;
        Long idRecurso = 1L;
        LocalDate dataAgendamento = LocalDate.of(2025, 10, 20);

        RecursoComum recursoExistente = new RecursoComum(idRecurso, "Churrasqueira", null);
        Agendamento agendamentoExistente = new Agendamento(1L, dataAgendamento, "CONFIRMADO", new Morador(), recursoExistente);

        when(agendamentoRepository.existsByRecursoIdAndDataAgendamento(idRecurso, dataAgendamento))
                .thenReturn(true);


        AgendamentoException exception = assertThrows(
                AgendamentoException.class,
                () -> agendamentoService.agendar(idMorador, idRecurso, dataAgendamento)
        );

        assertEquals("Este recurso já está agendado para a data selecionada.", exception.getMessage());

        verify(agendamentoRepository, never()).save(any(Agendamento.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar agendar para uma data no passado")
    void deveLancarExcecao_AoAgendarParaDataNoPassado() {
        Long idMorador = 1L;
        Long idRecurso = 1L;
        LocalDate dataNoPassado = LocalDate.now().minusDays(1);

        AgendamentoException exception = assertThrows(
                AgendamentoException.class,
                () -> agendamentoService.agendar(idMorador, idRecurso, dataNoPassado)
        );

        assertEquals("Não é permitido fazer agendamentos para datas passadas.", exception.getMessage());
        verify(agendamentoRepository, never()).save(any(Agendamento.class));
    }

    @Test
    @DisplayName("Deve buscar um agendamento por ID com sucesso")
    void deveBuscarAgendamentoPorId_ComSucesso() {
        Long idAgendamento = 1L;
        Agendamento agendamentoMock = new Agendamento(idAgendamento, LocalDate.now(), "CONFIRMADO", new Morador(), new RecursoComum());

        when(agendamentoRepository.findById(idAgendamento)).thenReturn(Optional.of(agendamentoMock));

        Agendamento agendamentoEncontrado = agendamentoService.buscarPorId(idAgendamento);

        assertNotNull(agendamentoEncontrado);
        assertEquals(idAgendamento, agendamentoEncontrado.getId());
        verify(agendamentoRepository, times(1)).findById(idAgendamento);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar agendamento com ID inexistente")
    void deveLancarExcecao_AoBuscarAgendamentoComIdInexistente() {
        Long idInexistente = 99L;
        when(agendamentoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> agendamentoService.buscarPorId(idInexistente)
        );

        assertEquals("Agendamento não encontrado para o ID: " + idInexistente, exception.getMessage());
    }
}