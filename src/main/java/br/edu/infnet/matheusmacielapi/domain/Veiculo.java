package br.edu.infnet.matheusmacielapi.domain;

public class Veiculo {

    private Long id;
    private String placa;
    private String modelo;
    private String cor;

    public Veiculo(Long id, String placa, String modelo, String cor) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
    }

    public Veiculo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    @Override
    public String toString() {
        return String.format(
                "ID: %d, Placa: %s, Modelo: %s, Cor: %s",
                id, placa, modelo, cor
        );
    }


}
