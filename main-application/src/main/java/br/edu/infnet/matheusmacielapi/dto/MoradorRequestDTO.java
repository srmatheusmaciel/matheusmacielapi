package br.edu.infnet.matheusmacielapi.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MoradorRequestDTO {

    @NotBlank(message = "O nome não pode ser vazio ou nulo.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O documento не pode ser vazio ou nulo.")
    private String documento;

    @NotBlank(message = "O telefone não pode ser vazio ou nulo.")
    @Pattern(
            regexp = "^\\(?\\d{2}\\)?\\s?(?:9\\d{4}|\\d{4})-?\\d{4}$",
            message = "Telefone inválido. Use o formato (XX)XXXXX-XXXX ou (XX)XXXX-XXXX"
    )
    private String telefone;

    @Email(message = "O formato do email é inválido.")
    @NotBlank(message = "O email não pode ser vazio ou nulo.")
    private String email;

    @NotNull(message = "O ID da unidade é obrigatório.")
    private Long unidadeId;

    @NotNull(message = "O status de proprietário é obrigatório.")
    private boolean proprietario;

    @Positive(message = "A taxa de condomínio deve ser um valor positivo.")
    private double taxaCondominio;
}