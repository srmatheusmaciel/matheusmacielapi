package br.edu.infnet.matheusmacielapi.repository;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {
}
