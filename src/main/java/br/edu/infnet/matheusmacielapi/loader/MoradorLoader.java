package br.edu.infnet.matheusmacielapi.loader;

import br.edu.infnet.matheusmacielapi.domain.Bloco;
import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Unidade;
import br.edu.infnet.matheusmacielapi.service.MoradorService;
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

    private final MoradorService moradorService;

    public MoradorLoader(MoradorService moradorService) {
        this.moradorService = moradorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> [MoradorLoader] Iniciando carregamento de Blocos, Unidades e Moradores...");

        Map<Long, Bloco> blocosCarregados = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("dados/blocos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Bloco bloco = new Bloco(Long.parseLong(campos[0]), campos[1]);
                blocosCarregados.put(bloco.getId(), bloco);
            }
        }

        Map<Long, Unidade> unidadesCarregadas = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("dados/unidades.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Unidade unidade = new Unidade(
                        Long.parseLong(campos[0]), campos[1], campos[2],
                        blocosCarregados.get(Long.parseLong(campos[3]))
                );
                unidadesCarregadas.put(unidade.getId(), unidade);
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader("dados/moradores.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Morador morador = new Morador(
                        campos[0], campos[1], campos[2], campos[3],
                        unidadesCarregadas.get(Long.parseLong(campos[4])),
                        Boolean.parseBoolean(campos[5]),
                        Double.parseDouble(campos[6])
                );
                moradorService.salvar(morador);
            }
        }
        System.out.println(">>> [MoradorLoader] Carga finalizada.");
    }
}