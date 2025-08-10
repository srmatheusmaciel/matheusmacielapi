package br.edu.infnet.matheusmacielapi.repository;

import br.edu.infnet.matheusmacielapi.domain.RegistroAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroAcessoRepository extends JpaRepository<RegistroAcesso, Long> {
}
