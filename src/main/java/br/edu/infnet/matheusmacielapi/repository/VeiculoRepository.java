package br.edu.infnet.matheusmacielapi.repository;

import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    boolean existsByPlacaIgnoreCase(String placa);
}
