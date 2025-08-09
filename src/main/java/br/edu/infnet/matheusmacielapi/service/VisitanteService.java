package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.RegistroAcesso;
import br.edu.infnet.matheusmacielapi.domain.Visitante;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VisitanteService implements CrudService<Visitante, Long> {

    private static Map<Long, Visitante> visitantes = new ConcurrentHashMap<>();
    private static AtomicLong contadorDeIds = new AtomicLong(0);

    private final RegistroAcessoService registroAcessoService;

    public VisitanteService(RegistroAcessoService registroAcessoService) {
        this.registroAcessoService = registroAcessoService;
    }


    @Override
    public Visitante salvar(Visitante entidade) {
        Long id = contadorDeIds.incrementAndGet();
        entidade.setId(id);
        visitantes.put(id, entidade);
        return entidade;
    }

    @Override
    public Visitante buscarPorId(Long id) {
        Visitante visitante = visitantes.get(id);
        if(visitante == null){
            throw new ResourceNotFoundException("Visitante nao encontrado para o ID: " + id);
        }
        return visitante;
    }

    @Override
    public void excluir(Long id) {
        buscarPorId(id);
        visitantes.remove(id);
    }

    @Override
    public Collection<Visitante> listarTodos() {
        return visitantes.values();
    }

    @Override
    public Visitante atualizar(Long id, Visitante visitanteAtualizado) {
        buscarPorId(id);
        visitanteAtualizado.setId(id);
        visitantes.put(id, visitanteAtualizado);
        return null;
    }

    public RegistroAcesso registrarEntrada(Long idVisitante) {

        Visitante visitante = buscarPorId(idVisitante);
        RegistroAcesso novoAcesso = new RegistroAcesso();
        RegistroAcesso acessoSalvo = registroAcessoService.salvar(novoAcesso);
        visitante.getAcessos().add(acessoSalvo);

        return acessoSalvo;
    }
}
