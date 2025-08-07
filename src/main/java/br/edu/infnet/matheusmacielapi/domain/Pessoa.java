package br.edu.infnet.matheusmacielapi.domain;

public abstract class Pessoa {

    private Long id;

    private String nome;

    private String documento;

    private String telefone;

    private String email;

    public Pessoa(String nome, String documento, String telefone, String email) {
        this.nome = nome;
        this.documento = documento;
        this.telefone = telefone;
        this.email = email;
    }

    public Pessoa() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Nome: %s | Documento: %s | Telefone: %s | Email: %s",
                this.id,
                this.nome,
                this.documento,
                this.telefone,
                this.email
        );
    }
}
