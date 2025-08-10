package br.edu.infnet.matheusmacielapi.repository;

import br.edu.infnet.matheusmacielapi.domain.Bloco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlocoRepository extends JpaRepository<Bloco, Long> {
}
