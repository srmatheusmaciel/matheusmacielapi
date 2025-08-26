package br.edu.infnet.matheusmacielapi.loader;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Ocorrencia;
import br.edu.infnet.matheusmacielapi.repository.MoradorRepository;
import br.edu.infnet.matheusmacielapi.repository.OcorrenciaRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Optional;

@Component
@Order(4)
public class OcorrenciaLoader implements ApplicationRunner {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final MoradorRepository moradorRepository;

    public OcorrenciaLoader(OcorrenciaRepository ocorrenciaRepository, MoradorRepository moradorRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.moradorRepository = moradorRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> [OcorrenciaLoader] Iniciando carregamento de Ocorrências...");

        try (BufferedReader br = new BufferedReader(new FileReader("dados/ocorrencias.txt"))) {
            br.lines().forEach(linha -> {
                String[] campos = linha.split(";");
                Long idMorador = Long.parseLong(campos[0]);

                Optional<Morador> moradorOpt = moradorRepository.findById(idMorador);

                if (moradorOpt.isPresent()) {
                    Ocorrencia ocorrencia = new Ocorrencia(
                            null,
                            campos[1],
                            campos[2],
                            LocalDate.parse(campos[3]),
                            campos[4],
                            moradorOpt.get()
                    );
                    ocorrenciaRepository.save(ocorrencia);
                }
            });
        }
        System.out.println(">>> [OcorrenciaLoader] Carga finalizada.");
    }
}