package br.edu.infnet.matheusmacielapi.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_visitante")
public class Visitante extends Pessoa{

    private String rg;
    private String autorizadoPor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "visitante_id")
    private List<RegistroAcesso> acessos = new ArrayList<>();

    public Visitante(String nome, String documento, String telefone, String email, String rg, String autorizadoPor) {
        super(nome, documento, telefone, email);
        this.rg = rg;
        this.autorizadoPor = autorizadoPor;
    }

    public Visitante() {
        super();
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getAutorizadoPor() {
        return autorizadoPor;
    }

    public void setAutorizadoPor(String autorizadoPor) {
        this.autorizadoPor = autorizadoPor;
    }

    public List<RegistroAcesso> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<RegistroAcesso> acessos) {
        this.acessos = acessos;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | RG: %s | Autorizado Por: %s | Total de Acessos: %d",
                super.toString(),
                this.rg,
                this.autorizadoPor,
                this.acessos.size()
        );
    }
}
