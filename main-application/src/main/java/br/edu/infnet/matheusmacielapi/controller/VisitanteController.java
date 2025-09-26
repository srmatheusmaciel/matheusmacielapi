package br.edu.infnet.matheusmacielapi.controller;

import br.edu.infnet.matheusmacielapi.domain.RegistroAcesso;
import br.edu.infnet.matheusmacielapi.domain.Visitante;
import br.edu.infnet.matheusmacielapi.service.VisitanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/visitantes")
public class VisitanteController {

    private final VisitanteService visitanteService;

    public VisitanteController(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }

    @GetMapping
    public ResponseEntity<Collection<Visitante>> listarTodos() {
        return ResponseEntity.ok(visitanteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitante> buscarPorId(@PathVariable Long id) {
        Visitante visitante = visitanteService.buscarPorId(id);
        return ResponseEntity.ok(visitante);
    }

    @PostMapping
    public ResponseEntity<Visitante> salvar(@RequestBody Visitante visitante) {
        Visitante visitanteSalvo = visitanteService.salvar(visitante);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(visitanteSalvo.getId()).toUri();

        return ResponseEntity.created(uri).body(visitanteSalvo);
    }

    @PostMapping("/{id}/registrar-entrada")
    public ResponseEntity<RegistroAcesso> registrarEntrada(@PathVariable Long id) {
        RegistroAcesso novoAcesso = visitanteService.registrarEntrada(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAcesso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visitante> atualizar(@PathVariable Long id, @RequestBody Visitante visitante) {
        Visitante visitanteAtualizado = visitanteService.atualizar(id, visitante);
        return ResponseEntity.ok(visitanteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        visitanteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
