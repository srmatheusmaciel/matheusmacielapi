package br.edu.infnet.matheusmacielapi.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Morador extends Pessoa{

    private Unidade unidade;
    private boolean proprietario;
    private double taxaCondominio;

    private List<Veiculo> veiculos = new ArrayList<>();

    public Morador(String nome,
                   String documento,
                   String telefone,
                   String email,
                   Unidade unidade,
                   boolean proprietario,
                   double taxaCondominio) {
        super(nome, documento, telefone, email);
        this.unidade = unidade;
        this.proprietario = proprietario;
        this.taxaCondominio = taxaCondominio;
    }

    public Morador() {
        super();
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public boolean isProprietario() {
        return proprietario;
    }

    public void setProprietario(boolean proprietario) {
        this.proprietario = proprietario;
    }

    public double getTaxaCondominio() {
        return taxaCondominio;
    }

    public void setTaxaCondominio(double taxaCondominio) {
        this.taxaCondominio = taxaCondominio;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    @Override
    public String toString() {
        String placasVeiculos = this.veiculos.stream()
                .map(Veiculo::getPlaca)
                .collect(Collectors.joining(", ", "[", "]"));

        return String.format(
                "%s | Unidade: %s (Bloco %s) | Proprietário: %s | Taxa Condomínio: R$%.2f | Veículos: %s",
                super.toString(),
                this.unidade.getNumero(),
                this.unidade.getBloco().getNome(),
                this.proprietario ? "Sim" : "Não",
                this.taxaCondominio,
                placasVeiculos
        );
    }

}
