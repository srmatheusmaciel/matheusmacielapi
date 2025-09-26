package br.edu.infnet.matheusmacielapi.client.dto;

import lombok.Data;

@Data
public class VerificacaoFeriadoDTO {
    private boolean isHoliday;
    private String holidayName;
}