package br.edu.infnet.matheusmacielapi.module.service;

import br.edu.infnet.matheusmacielapi.module.agendamento.domain.Agendamento;
import br.edu.infnet.matheusmacielapi.module.agendamento.domain.RecursoComum;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.AgendamentoRepository;
import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.repository.MoradorRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private MoradorRepository moradorRepository;

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

        when(moradorRepository.findById(idMorador)).thenReturn(Optional.of(moradorMock));

        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamentoSalvoMock);


        Agendamento agendamentoRealizado = agendamentoService.agendar(idMorador, idRecurso, dataAgendamento);


        assertNotNull(agendamentoRealizado);
        assertEquals("CONFIRMADO", agendamentoRealizado.getStatus());
        assertEquals(idMorador, agendamentoRealizado.getMorador().getId());
        assertEquals(idRecurso, agendamentoRealizado.getRecurso().getId());
    }
}