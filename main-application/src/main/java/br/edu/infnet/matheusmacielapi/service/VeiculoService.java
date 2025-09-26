package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import br.edu.infnet.matheusmacielapi.infra.exception.ValidacaoNegocioException;
import br.edu.infnet.matheusmacielapi.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional(readOnly = true)
    public Collection<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    @Transactional
    public Veiculo salvar(Veiculo veiculo) {
        if (veiculoRepository.existsByPlacaIgnoreCase(veiculo.getPlaca())) {
            throw new ValidacaoNegocioException("A placa '" + veiculo.getPlaca() + "' já está cadastrada no sistema.");
        }
        return veiculoRepository.save(veiculo);
    }
}