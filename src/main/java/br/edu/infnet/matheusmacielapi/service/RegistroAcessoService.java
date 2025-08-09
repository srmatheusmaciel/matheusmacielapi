package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.RegistroAcesso;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RegistroAcessoService {

    private final Map<Long, RegistroAcesso> acessos = new ConcurrentHashMap<>();
    private final AtomicLong contadorDeIds = new AtomicLong(0);

    public RegistroAcesso salvar(RegistroAcesso registroAcesso) {
        Long id = contadorDeIds.incrementAndGet();
        registroAcesso.setId(id);
        acessos.put(id, registroAcesso);
        return registroAcesso;
    }

    public RegistroAcesso buscarPorId(Long id) {
        RegistroAcesso registro = acessos.get(id);
        if (registro == null) {
            throw new ResourceNotFoundException("Registro de Acesso não encontrado para o ID: " + id);
        }
        return registro;
    }

    public void registrarSaida(Long idAcesso) {
        RegistroAcesso registro = buscarPorId(idAcesso);
        registro.setDataHoraSaida(LocalDateTime.now());
        acessos.put(idAcesso, registro);
    }
}