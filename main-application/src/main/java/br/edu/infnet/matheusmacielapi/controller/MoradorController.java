package br.edu.infnet.matheusmacielapi.controller;

import br.edu.infnet.matheusmacielapi.domain.Morador;
import br.edu.infnet.matheusmacielapi.domain.Veiculo;
import br.edu.infnet.matheusmacielapi.dto.MoradorRequestDTO;
import br.edu.infnet.matheusmacielapi.dto.MoradorResponseDTO;
import br.edu.infnet.matheusmacielapi.service.MoradorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Collection<MoradorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(moradorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoradorResponseDTO> buscarPorId(@PathVariable Long id) {
        MoradorResponseDTO morador = moradorService.buscarPorId(id);
        return ResponseEntity.ok(morador);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Collection<MoradorResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(moradorService.buscarPorNome(nome));
    }

    @GetMapping("/buscar/proprietario")
    public ResponseEntity<Collection<MoradorResponseDTO>> buscarPorNomeEStatusProprietario(
            @RequestParam String nome,
            @RequestParam boolean proprietario
    ) {
        return ResponseEntity.ok(moradorService.buscarPorNomeEStatusProprietario(nome, proprietario));
    }

    @GetMapping("/buscar/localizacao-veiculo")
    public ResponseEntity<Collection<MoradorResponseDTO>> buscarPorBlocoEVeiculo(
            @RequestParam String bloco,
            @RequestParam String cor
    ) {
        return ResponseEntity.ok(moradorService.buscarPorBlocoEVeiculo(bloco, cor));
    }

    @PostMapping
    public ResponseEntity<MoradorResponseDTO> salvar(@Valid @RequestBody MoradorRequestDTO moradorDTO) {
        MoradorResponseDTO moradorSalvo = moradorService.salvar(moradorDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(moradorSalvo.getId()).toUri();

        return ResponseEntity.created(uri).body(moradorSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoradorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MoradorRequestDTO moradorDTO) {
        MoradorResponseDTO moradorAtualizado = moradorService.atualizar(id, moradorDTO);
        return ResponseEntity.ok(moradorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        moradorService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{moradorId}/veiculos")
    public ResponseEntity<Veiculo> adicionarVeiculo(@PathVariable Long moradorId,@Valid @RequestBody Veiculo veiculo) {
        Veiculo veiculoSalvo = moradorService.adicionarVeiculo(moradorId, veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoSalvo);
    }

    @DeleteMapping("/{idMorador}/veiculos/{idVeiculo}")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable Long idMorador, @PathVariable Long idVeiculo) {
        moradorService.excluirVeiculo(idMorador, idVeiculo);
        return ResponseEntity.noContent().build();
    }
}