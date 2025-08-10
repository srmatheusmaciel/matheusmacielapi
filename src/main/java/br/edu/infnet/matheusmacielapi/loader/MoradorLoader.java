package br.edu.infnet.matheusmacielapi.loader;

import br.edu.infnet.matheusmacielapi.domain.*;
import br.edu.infnet.matheusmacielapi.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(1)
public class MoradorLoader implements ApplicationRunner {

    private final BlocoRepository blocoRepository;
    private final UnidadeRepository unidadeRepository;
    private final MoradorRepository moradorRepository;

    public MoradorLoader(BlocoRepository blocoRepository, UnidadeRepository unidadeRepository, MoradorRepository moradorRepository) {
        this.blocoRepository = blocoRepository;
        this.unidadeRepository = unidadeRepository;
        this.moradorRepository = moradorRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> [MoradorLoader] Iniciando carga de Blocos, Unidades e Moradores...");

        Map<Long, Bloco> blocosCarregados = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("dados/blocos.txt"))) {
            br.lines().forEach(linha -> {
                String[] campos = linha.split(";");
                Bloco bloco = new Bloco(null, campos[1]);
                blocoRepository.save(bloco);
                blocosCarregados.put(Long.parseLong(campos[0]), bloco);
            });
        }

        Map<Long, Unidade> unidadesCarregadas = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("dados/unidades.txt"))) {
            br.lines().forEach(linha -> {
                String[] campos = linha.split(";");
                Bloco blocoAssociado = blocosCarregados.get(Long.parseLong(campos[3]));
                Unidade unidade = new Unidade(null, campos[1], campos[2], blocoAssociado);
                unidadeRepository.save(unidade);
                unidadesCarregadas.put(Long.parseLong(campos[0]), unidade);
            });
        }

        try (BufferedReader br = new BufferedReader(new FileReader("dados/moradores.txt"))) {
            br.lines().forEach(linha -> {
                String[] campos = linha.split(";");
                Unidade unidadeAssociada = unidadesCarregadas.get(Long.parseLong(campos[4]));
                Morador morador = new Morador(
                        campos[0], campos[1], campos[2], campos[3],
                        unidadeAssociada,
                        Boolean.parseBoolean(campos[5]),
                        Double.parseDouble(campos[6])
                );
                moradorRepository.save(morador);
            });
        }
        System.out.println(">>> [MoradorLoader] Carga finalizada.");
    }
}