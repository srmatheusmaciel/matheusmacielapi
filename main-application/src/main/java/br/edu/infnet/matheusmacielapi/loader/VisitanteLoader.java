package br.edu.infnet.matheusmacielapi.loader;

import br.edu.infnet.matheusmacielapi.domain.Visitante;
import br.edu.infnet.matheusmacielapi.repository.VisitanteRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@Order(3)
public class VisitanteLoader implements ApplicationRunner {

    private final VisitanteRepository visitanteRepository;

    public VisitanteLoader(VisitanteRepository visitanteRepository) {
        this.visitanteRepository = visitanteRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> [VisitanteLoader] Iniciando carregamento de Visitantes...");

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dados/visitantes.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            br.lines().forEach(linha -> {
                String[] campos = linha.split(";");
                Visitante visitante = new Visitante(
                        campos[0], campos[1], campos[2], campos[3], campos[4], campos[5]
                );
                visitanteRepository.save(visitante);
            });
        }
        System.out.println(">>> [VisitanteLoader] Carga finalizada.");
    }
}