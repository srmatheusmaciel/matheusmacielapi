package br.edu.infnet.matheusmacielapi.loader;

import br.edu.infnet.matheusmacielapi.domain.Visitante;
import br.edu.infnet.matheusmacielapi.service.VisitanteService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(3)
public class VisitanteLoader implements ApplicationRunner {

    private final VisitanteService visitanteService;

    public VisitanteLoader(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> [VisitanteLoader] Iniciando carregamento de Visitantes...");

        try (BufferedReader br = new BufferedReader(new FileReader("dados/visitantes.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Visitante visitante = new Visitante(
                        campos[0], campos[1], campos[2], campos[3], campos[4], campos[5]
                );
                visitanteService.salvar(visitante);
            }
        }
        System.out.println(">>> [VisitanteLoader] Carga finalizada.");
    }
}