package br.edu.infnet.matheusmacielapi.controller;

import br.edu.infnet.matheusmacielapi.service.RegistroAcessoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acessos") // Novo endpoint base
public class RegistroAcessoController {

    private final RegistroAcessoService registroAcessoService;

    public RegistroAcessoController(RegistroAcessoService registroAcessoService) {
        this.registroAcessoService = registroAcessoService;
    }

    @PatchMapping("/{idAcesso}/registrar-saida")
    public ResponseEntity<Void> registrarSaida(@PathVariable Long idAcesso) {
        registroAcessoService.registrarSaida(idAcesso);
        return ResponseEntity.ok().build();
    }
}