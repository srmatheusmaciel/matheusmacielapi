package br.edu.infnet.matheusmacielapi.loader;

import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import br.edu.infnet.matheusmacielapi.service.MoradorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dados/veiculos.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            br.lines().forEach(linha -> {
                String[] campos = linha.split(";");
                Long idMoradorDoArquivo = Long.parseLong(campos[0]);
                Veiculo veiculo = new Veiculo(null, campos[1], campos[2], campos[3]);

                try {
                    moradorService.adicionarVeiculo(idMoradorDoArquivo, veiculo);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar veículo do loader para o morador ID " + idMoradorDoArquivo + ": " + e.getMessage());
                }
            });
        }
        System.out.println(">>> [VeiculoLoader] Carga finalizada.");
    }
}