package br.edu.infnet.matheusmacielapi.module.agendamento.controller;

import br.edu.infnet.matheusmacielapi.dto.AgendamentoRequestDTO;
import br.edu.infnet.matheusmacielapi.dto.AgendamentoResponseDTO;
import br.edu.infnet.matheusmacielapi.module.agendamento.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public ResponseEntity<Collection<AgendamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        AgendamentoResponseDTO agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> agendar(@Valid @RequestBody AgendamentoRequestDTO agendamentoDTO) {
        AgendamentoResponseDTO novoAgendamento = agendamentoService.agendar(agendamentoDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoAgendamento.getId()).toUri();

        return ResponseEntity.created(uri).body(novoAgendamento);
    }

    @PutMapping("/{id}/data")
    public ResponseEntity<AgendamentoResponseDTO> atualizarData(@PathVariable Long id,
                                                                @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate novaData) {
        AgendamentoResponseDTO agendamentoAtualizado = agendamentoService.atualizarData(id, novaData);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        agendamentoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}