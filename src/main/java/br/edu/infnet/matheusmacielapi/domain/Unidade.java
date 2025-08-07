package br.edu.infnet.matheusmacielapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Unidade {

    private Long id;

    private String numero;

    private String descricao;

    private Bloco bloco;

}
