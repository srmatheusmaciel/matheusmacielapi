package br.edu.infnet.matheusmacielapi;

import br.edu.infnet.matheusmacielapi.service.MoradorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.edu.infnet.matheusmacielapi.domain.Bloco;
import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Unidade;

@Component
public class DataLoader implements ApplicationRunner {

    private final MoradorService moradorService;

    public DataLoader(MoradorService moradorService) {
        this.moradorService = moradorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>> [DataLoader] Iniciando carregamento de dados a partir de arquivos...");

        // Usaremos Maps para guardar os objetos criados e depois vinculá-los.
        Map<Long, Bloco> blocosCarregados = new HashMap<>();
        Map<Long, Unidade> unidadesCarregadas = new HashMap<>();

        // 1. Lendo blocos.txt
        try (BufferedReader br = new BufferedReader(new FileReader("dados/blocos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Long id = Long.parseLong(campos[0]);
                Bloco bloco = new Bloco(id, campos[1]);
                blocosCarregados.put(id, bloco);
            }
        }
        System.out.println(">>> [DataLoader] Carregados " + blocosCarregados.size() + " blocos.");


        // 2. Lendo unidades.txt
        try (BufferedReader br = new BufferedReader(new FileReader("dados/unidades.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Long idUnidade = Long.parseLong(campos[0]);
                Long idBloco = Long.parseLong(campos[3]);

                Bloco blocoAssociado = blocosCarregados.get(idBloco);
                if (blocoAssociado != null) {
                    Unidade unidade = new Unidade(idUnidade, campos[1], campos[2], blocoAssociado);
                    unidadesCarregadas.put(idUnidade, unidade);
                }
            }
        }
        System.out.println(">>> [DataLoader] Carregadas " + unidadesCarregadas.size() + " unidades.");


        // 3. Lendo moradores.txt e salvando no serviço
        try (BufferedReader br = new BufferedReader(new FileReader("dados/moradores.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                Long idUnidade = Long.parseLong(campos[4]);

                Unidade unidadeAssociada = unidadesCarregadas.get(idUnidade);
                if (unidadeAssociada != null) {
                    Morador morador = new Morador(
                            campos[0], // nome
                            campos[1], // documento
                            campos[2], // telefone
                            campos[3], // email
                            unidadeAssociada, // objeto Unidade completo
                            Boolean.parseBoolean(campos[5]), // proprietario
                            Double.parseDouble(campos[6])  // taxaCondominio
                    );
                    moradorService.salvar(morador);
                }
            }
        }
        System.out.println(">>> [DataLoader] Moradores salvos no sistema.");


        // 4. Confirmação da população
        System.out.println(">>> [DataLoader] Verificando a lista de moradores no sistema:");
        Collection<Morador> moradores = moradorService.listarTodos();
        for (Morador morador : moradores) {
            System.out.println(morador);
        }
    }
}