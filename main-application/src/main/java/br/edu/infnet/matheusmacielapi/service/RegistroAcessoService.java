package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.RegistroAcesso;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.repository.RegistroAcessoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class RegistroAcessoService {

    private final RegistroAcessoRepository registroAcessoRepository;

    public RegistroAcessoService(RegistroAcessoRepository registroAcessoRepository) {
        this.registroAcessoRepository = registroAcessoRepository;
    }

    @Transactional
    public RegistroAcesso salvar(RegistroAcesso registroAcesso) {
        return registroAcessoRepository.save(registroAcesso);
    }

    @Transactional(readOnly = true)
    public RegistroAcesso buscarPorId(Long id) {
        return registroAcessoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Acesso não encontrado para o ID: " + id));
    }

    @Transactional
    public void registrarSaida(Long idAcesso) {
        RegistroAcesso registro = buscarPorId(idAcesso);
        registro.setDataHoraSaida(LocalDateTime.now());
        registroAcessoRepository.save(registro);
    }
}