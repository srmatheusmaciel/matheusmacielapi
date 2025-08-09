package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MoradorService implements CrudService<Morador, Long> {

    private final Map<Long, Morador> moradores = new ConcurrentHashMap<>();
    private final AtomicLong contadorDeIds = new AtomicLong();

    private final VeiculoService veiculoService;

    public MoradorService(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }


    @Override
    public Morador salvar(Morador morador) {

        Objects.requireNonNull(morador, "O morador não pode ser nulo.");
        Objects.requireNonNull(morador.getUnidade(), "A unidade do morador não pode ser nula.");

        Long id = contadorDeIds.incrementAndGet();
        morador.setId(id);
        moradores.put(id, morador);


        return morador;
    }

    @Override
    public Morador buscarPorId(Long id) {
        Morador morador = moradores.get(id);

        if(morador == null){
            throw new ResourceNotFoundException("Morador não encontrado para o ID: " + id);
        }

        return moradores.get(id);
    }


    @Override
    public Collection<Morador> listarTodos() {
        return moradores.values();
    }

    @Override
    public Morador atualizar(Long id, Morador moradorAtualizado) {

        buscarPorId(id);

        moradorAtualizado.setId(id);

        moradores.put(id, moradorAtualizado);
        return moradorAtualizado;
    }

    @Override
    public void excluir(Long id) {
        buscarPorId(id);
        moradores.remove(id);
    }

    public Veiculo adicionarVeiculo(Long moradorId, Veiculo veiculo) {
        Morador morador = buscarPorId(moradorId);
        Veiculo veiculoSalvo = veiculoService.salvar(veiculo);
        morador.getVeiculos().add(veiculoSalvo);

        return veiculoSalvo;

    }
}
