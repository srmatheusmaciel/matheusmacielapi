package br.edu.infnet.matheusmacielapi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AgendamentoRequestDTO {

    @NotNull(message = "O ID do morador é obrigatório.")
    private Long idMorador;

    @NotNull(message = "O ID do recurso é obrigatório.")
    private Long idRecurso;

    @NotNull(message = "A data do agendamento é obrigatória.")
    @FutureOrPresent(message = "A data do agendamento não pode ser no passado.")
    private LocalDate dataAgendamento;
}