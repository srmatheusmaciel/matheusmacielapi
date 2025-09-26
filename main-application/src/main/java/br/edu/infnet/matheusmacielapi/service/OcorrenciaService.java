package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Ocorrencia;
import br.edu.infnet.matheusmacielapi.infra.exception.ResourceNotFoundException;
import br.edu.infnet.matheusmacielapi.repository.MoradorRepository; // 1. Importar o repositório
import br.edu.infnet.matheusmacielapi.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final MoradorRepository moradorRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, MoradorRepository moradorRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.moradorRepository = moradorRepository;
    }

    @Transactional(readOnly = true)
    public Collection<Ocorrencia> buscarTodos() {
        return ocorrenciaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<Ocorrencia> buscarPorStatusEData(String status, LocalDate dataInicio, LocalDate dataFim) {
        return ocorrenciaRepository.findByStatusAndDataBetween(status, dataInicio, dataFim);
    }

    @Transactional
    public Ocorrencia salvar(Long idMorador, Ocorrencia ocorrencia){
        Morador morador = moradorRepository.findById(idMorador)
                .orElseThrow(() -> new ResourceNotFoundException("Morador não encontrado para o ID: " + idMorador));

        ocorrencia.setMorador(morador);
        return ocorrenciaRepository.save(ocorrencia);
    }
}