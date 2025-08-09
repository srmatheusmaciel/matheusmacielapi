package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import br.edu.infnet.matheusmacielapi.infra.exception.ValidacaoNegocioException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VeiculoService {

    private final Map<Long, Veiculo> veiculos = new ConcurrentHashMap<>();
    private final AtomicLong contadorDeIds = new AtomicLong();

    public Collection<Veiculo> listarTodos() {
        return veiculos.values();
    }

    public Veiculo salvar(Veiculo veiculo) {
        if(PlacaJaExiste(veiculo.getPlaca())) {
            throw new ValidacaoNegocioException("A placa '" + veiculo.getPlaca() + "' já está cadastrada no sistema.");
        }
        Long id = contadorDeIds.incrementAndGet();
        veiculo.setId(id);
        veiculos.put(id, veiculo);
        return veiculo;
    }

    private boolean PlacaJaExiste(String placa) {
        return veiculos.values().stream()
                .anyMatch(v -> v.getPlaca().equalsIgnoreCase(placa));
    }

}
