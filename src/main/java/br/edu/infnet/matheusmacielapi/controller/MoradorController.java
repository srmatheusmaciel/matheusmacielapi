package br.edu.infnet.matheusmacielapi.controller;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.service.MoradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/moradores")
public class MoradorController {

    private final MoradorService moradorService;

    public MoradorController(MoradorService moradorService) {
        this.moradorService = moradorService;
    }

    @GetMapping
    public ResponseEntity<Collection<Morador>> listarTodos() {
        return ResponseEntity.ok(moradorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Morador> buscarPorId(@PathVariable Long id) {
        Morador morador = moradorService.buscarPorId(id);
        return ResponseEntity.ok(morador);
    }

    @PostMapping
    public ResponseEntity<Morador> salvar(@RequestBody Morador morador) {
        Morador moradorSalvo = moradorService.salvar(morador);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(moradorSalvo.getId()).toUri();

        return ResponseEntity.created(uri).body(moradorSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Morador> atualizar(@PathVariable Long id, @RequestBody Morador morador) {
        Morador moradorAtualizado = moradorService.atualizar(id, morador);
        return ResponseEntity.ok(moradorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        moradorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}