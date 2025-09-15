package br.edu.infnet.matheusmacielapi.module.agendamento.controller;

import br.edu.infnet.matheusmacielapi.module.agendamento.domain.Agendamento;
import br.edu.infnet.matheusmacielapi.module.agendamento.dto.AgendamentoRequestDTO;
import br.edu.infnet.matheusmacielapi.module.agendamento.service.AgendamentoService;
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
    public ResponseEntity<Collection<Agendamento>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        Agendamento agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    public ResponseEntity<Agendamento> agendar(@RequestBody AgendamentoRequestDTO agendamentoDTO) {
        Agendamento novoAgendamento = agendamentoService.agendar(
                agendamentoDTO.getIdMorador(),
                agendamentoDTO.getIdRecurso(),
                agendamentoDTO.getDataAgendamento()
        );

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoAgendamento.getId()).toUri();

        return ResponseEntity.created(uri).body(novoAgendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizar(@PathVariable Long id, @RequestBody LocalDate novaData) {
        Agendamento agendamentoAtualizado = agendamentoService.atualizar(id, novaData);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        agendamentoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}