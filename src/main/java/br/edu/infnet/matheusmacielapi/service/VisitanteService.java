package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.RegistroAcesso;
import br.edu.infnet.matheusmacielapi.domain.Visitante;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.repository.VisitanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Service
public class VisitanteService {

    private final VisitanteRepository visitanteRepository;
    private final RegistroAcessoService registroAcessoService;

    public VisitanteService(VisitanteRepository visitanteRepository, RegistroAcessoService registroAcessoService) {
        this.visitanteRepository = visitanteRepository;
        this.registroAcessoService = registroAcessoService;
    }

    @Transactional(readOnly = true)
    public Collection<Visitante> listarTodos() {
        return visitanteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Visitante buscarPorId(Long id) {
        return visitanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitante não encontrado para o ID: " + id));
    }

    @Transactional
    public Visitante salvar(Visitante visitante) {
        return visitanteRepository.save(visitante);
    }

    @Transactional
    public Visitante atualizar(Long id, Visitante visitanteAtualizado) {
        buscarPorId(id);
        visitanteAtualizado.setId(id);
        return visitanteRepository.save(visitanteAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        visitanteRepository.deleteById(id);
    }

    @Transactional
    public RegistroAcesso registrarEntrada(Long idVisitante) {
        Visitante visitante = buscarPorId(idVisitante);
        RegistroAcesso novoAcesso = new RegistroAcesso();

        RegistroAcesso acessoSalvo = registroAcessoService.salvar(novoAcesso);

        visitante.getAcessos().add(acessoSalvo);
        visitanteRepository.save(visitante);

        return acessoSalvo;
    }
}