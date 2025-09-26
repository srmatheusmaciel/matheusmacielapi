package br.edu.infnet.matheusmacielapi.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AgendamentoResponseDTO {

    private Long id;
    private LocalDate dataAgendamento;
    private String status;
    private String nomeMorador;
    private String nomeRecurso;

    private boolean isFeriado;
    private String nomeFeriado;
}