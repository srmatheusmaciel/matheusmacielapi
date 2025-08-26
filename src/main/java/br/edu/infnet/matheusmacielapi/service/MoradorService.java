package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.repository.MoradorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Service
public class MoradorService {

    private final MoradorRepository moradorRepository;
    private final VeiculoService veiculoService;

    public MoradorService(MoradorRepository moradorRepository, VeiculoService veiculoService) {
        this.moradorRepository = moradorRepository;
        this.veiculoService = veiculoService;
    }

    @Transactional(readOnly = true)
    public Collection<Morador> listarTodos() {
        return moradorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Morador buscarPorId(Long id) {
        return moradorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Morador não encontrado para o ID: " + id));
    }

    @Transactional(readOnly = true)
    public Collection<Morador> buscarPorNome(String nome) {
        return moradorRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public Morador salvar(Morador morador) {
        return moradorRepository.save(morador);
    }

    @Transactional
    public Morador atualizar(Long id, Morador moradorAtualizado) {
        buscarPorId(id);
        moradorAtualizado.setId(id);
        return moradorRepository.save(moradorAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        Morador morador = buscarPorId(id);
        moradorRepository.delete(morador);
    }

    @Transactional
    public Veiculo adicionarVeiculo(Long idMorador, Veiculo veiculo) {
        Morador morador = buscarPorId(idMorador);

        Veiculo veiculoSalvo = veiculoService.salvar(veiculo);

        morador.getVeiculos().add(veiculoSalvo);
        moradorRepository.save(morador);

        return veiculoSalvo;
    }

    @Transactional
    public void excluirVeiculo(Long idMorador, Long idVeiculo) {
        Morador morador = buscarPorId(idMorador);

        Veiculo veiculoParaExcluir = morador.getVeiculos().stream()
                .filter(v -> v.getId().equals(idVeiculo))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Veículo com ID " + idVeiculo + " não encontrado para o morador com ID " + idMorador
                ));

        morador.getVeiculos().remove(veiculoParaExcluir);

        moradorRepository.save(morador);
    }
}