package br.edu.infnet.matheusmacielapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "TB_OCORRENCIA")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título da ocorrência é obrigatório.")
    @Size(min = 5, max = 150, message = "O título deve ter entre 5 e 150 caracteres.")
    private String titulo;

    @NotBlank(message = "A descrição da ocorrência é obrigatória.")
    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres.")
    private String descricao;

    @NotNull(message = "A data da ocorrência é obrigatória.")
    @PastOrPresent(message = "A data da ocorrência não pode ser no futuro.")
    private LocalDate data;

    @NotBlank(message = "O status da ocorrência é obrigatório.")
    private String status; // Ex: "ABERTA", "EM ANDAMENTO", "RESOLVIDA"

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;

    public Ocorrencia() {}

    public Ocorrencia(Long id, String titulo, String descricao, LocalDate data, String status, Morador morador) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
        this.morador = morador;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Morador getMorador() { return morador; }
    public void setMorador(Morador morador) { this.morador = morador; }
}