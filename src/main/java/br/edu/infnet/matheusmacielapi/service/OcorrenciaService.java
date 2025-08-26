package br.edu.infnet.matheusmacielapi.service;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Ocorrencia;
import br.edu.infnet.matheusmacielapi.repository.OcorrenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final MoradorService moradorService;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository, MoradorService moradorService) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.moradorService = moradorService;
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
        Morador morador = moradorService.buscarPorId(idMorador);
        ocorrencia.setMorador(morador);
        return ocorrenciaRepository.save(ocorrencia);
    }
}
