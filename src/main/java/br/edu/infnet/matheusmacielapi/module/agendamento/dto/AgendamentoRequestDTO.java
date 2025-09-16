package br.edu.infnet.matheusmacielapi.module.agendamento.dto;

import java.time.LocalDate;

public class AgendamentoRequestDTO {

    private Long idMorador;
    private Long idRecurso;
    private LocalDate dataAgendamento;

    public Long getIdMorador() {
        return idMorador;
    }
    public void setIdMorador(Long idMorador) {
        this.idMorador = idMorador;
    }
    public Long getIdRecurso() {
        return idRecurso;
    }
    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }
    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }
    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
}