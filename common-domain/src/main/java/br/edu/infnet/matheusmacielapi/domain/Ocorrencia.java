package br.edu.infnet.matheusmacielapi.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_OCORRENCIA")
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private LocalDate data;

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