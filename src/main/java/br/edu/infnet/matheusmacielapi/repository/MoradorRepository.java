package br.edu.infnet.matheusmacielapi.repository;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {
    List<Morador> findByNomeContainingIgnoreCase(String nome);
    List<Morador> findByNomeContainingIgnoreCaseAndProprietario(String nome, boolean proprietario);
    List<Morador> findByUnidadeBlocoNomeContainingIgnoreCaseAndVeiculosCorIgnoreCase(String nomeBloco, String corVeiculo);
}
