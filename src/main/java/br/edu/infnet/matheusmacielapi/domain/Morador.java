package br.edu.infnet.matheusmacielapi.domain;

public class Morador extends Pessoa{

    private Unidade unidade;
    private boolean proprietario;
    private double taxaCondominio;

    public Morador(String nome, String documento, String telefone, String email, Unidade unidade, boolean proprietario, double taxaCondominio) {
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

    @Override
    public String toString() {
        return String.format(
                "%s | Unidade: %s (Bloco %s) | Proprietário: %s | Taxa Condomínio: R$%.2f",
                super.toString(),
                this.unidade.getNumero(),
                this.unidade.getBloco().getNome(),
                this.proprietario ? "Sim" : "Não",
                this.taxaCondominio
        );
    }

}
