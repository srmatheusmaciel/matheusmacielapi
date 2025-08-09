package br.edu.infnet.matheusmacielapi.loader;

import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import br.edu.infnet.matheusmacielapi.service.MoradorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(2)
public class VeiculoLoader implements ApplicationRunner {

    private final MoradorService moradorService;

    public VeiculoLoader(MoradorService moradorService) {
        this.moradorService = moradorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> [VeiculoLoader] Iniciando carregamento e associação de Veículos...");

        try (BufferedReader br = new BufferedReader(new FileReader("dados/veiculos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Long idMorador = Long.parseLong(campos[0]);
                Veiculo veiculo = new Veiculo(null, campos[1], campos[2], campos[3]);
                try {
                    moradorService.adicionarVeiculo(idMorador, veiculo);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar veículo do loader: " + e.getMessage());
                }
            }
        }
        System.out.println(">>> [VeiculoLoader] Carga finalizada.");
    }
}