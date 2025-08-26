package br.edu.infnet.matheusmacielapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O nome não pode ser vazio ou nulo.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O documento não pode ser vazio ou nulo.")
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
