package br.edu.infnet.matheusmacielapi.client;

import br.edu.infnet.matheusmacielapi.client.dto.VerificacaoFeriadoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "feriadoApi", url = "http://localhost:8081/api/feriados")
public interface FeriadoApiClient {

    @GetMapping("/verificar")
    VerificacaoFeriadoDTO verificarFeriado(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data);
}