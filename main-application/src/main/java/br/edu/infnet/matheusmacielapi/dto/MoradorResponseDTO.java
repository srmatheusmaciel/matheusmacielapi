package br.edu.infnet.matheusmacielapi.dto;

import lombok.Data;

@Data
public class MoradorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private UnidadeResponseDTO unidade;
    private String statusProprietario;

    @Data
    public static class UnidadeResponseDTO {
        private String numero;
        private String nomeBloco;
    }
}