package br.edu.infnet.matheusmacielapi.module.agendamento.loader;

import br.edu.infnet.matheusmacielapi.module.agendamento.domain.RecursoComum;
import br.edu.infnet.matheusmacielapi.module.agendamento.repository.RecursoComumRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class RecursoComumLoader implements ApplicationRunner {

    private final RecursoComumRepository recursoComumRepository;

    public RecursoComumLoader(RecursoComumRepository recursoComumRepository) {
        this.recursoComumRepository = recursoComumRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (recursoComumRepository.count() == 0) {
            System.out.println(">>> [RecursoComumLoader] Nenhum recurso encontrado, populando dados iniciais...");

            RecursoComum churrasqueira = new RecursoComum(null, "Churrasqueira Principal", "Localizada perto da piscina. Capacidade para 30 pessoas.");
            recursoComumRepository.save(churrasqueira);

            RecursoComum salaoFestas = new RecursoComum(null, "Salão de Festas - Bloco A", "Equipado com cozinha e banheiros. Capacidade para 100 pessoas.");
            recursoComumRepository.save(salaoFestas);

            RecursoComum quadra = new RecursoComum(null, "Quadra Poliesportiva", "Disponível para futebol, basquete e vôlei.");
            recursoComumRepository.save(quadra);

            System.out.println(">>> [RecursoComumLoader] Carga de recursos comuns finalizada.");
        } else {
            System.out.println(">>> [RecursoComumLoader] Recursos comuns já carregados.");
        }
    }
}