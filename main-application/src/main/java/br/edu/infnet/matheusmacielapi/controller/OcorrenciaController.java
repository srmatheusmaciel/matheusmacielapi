package br.edu.infnet.matheusmacielapi.controller;

import br.edu.infnet.matheusmacielapi.domain.Ocorrencia;
import br.edu.infnet.matheusmacielapi.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @GetMapping
    public ResponseEntity<Collection<Ocorrencia>> buscarTodos() {
        return ResponseEntity.ok(ocorrenciaService.buscarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Collection<Ocorrencia>> buscar(
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    ) {
        return ResponseEntity.ok(ocorrenciaService.buscarPorStatusEData(status, dataInicio, dataFim));
    }

    @PostMapping("/morador/{idMorador}")
    public ResponseEntity<Ocorrencia> salvar(@PathVariable Long idMorador,
                                             @Valid @RequestBody Ocorrencia ocorrencia) {
        Ocorrencia ocorrenciaSalva = ocorrenciaService.salvar(idMorador, ocorrencia);
        return ResponseEntity.status(201).body(ocorrenciaSalva);
    }
}
