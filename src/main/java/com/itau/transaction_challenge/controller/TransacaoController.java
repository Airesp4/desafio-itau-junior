package com.itau.transaction_challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itau.transaction_challenge.DTO.EstatisticaDTO;
import com.itau.transaction_challenge.DTO.TransacaoDTO;
import com.itau.transaction_challenge.model.Transacao;
import com.itau.transaction_challenge.service.EstatisticaService;
import com.itau.transaction_challenge.service.TransacaoService;

import jakarta.validation.Valid;

@RestController
public class TransacaoController {
    
    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private EstatisticaService estatisticaService;

    @PostMapping("/transacao")
    public ResponseEntity<Void> receberTransacao(@RequestBody @Valid TransacaoDTO transacaoDTO) {
        try {
            Transacao novaTransacao = new Transacao(transacaoDTO);
            transacaoService.registrarTransacao(novaTransacao);
            
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {

            return ResponseEntity.unprocessableEntity().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/transacao")
    public ResponseEntity<Void> limparTransacoes(){

        transacaoService.deletarTransacoes();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    public ResponseEntity<EstatisticaDTO> calcularEstatisticas(){

        EstatisticaDTO estatistica = estatisticaService.pegaEstatistica();

        return ResponseEntity.ok().body(estatistica);
    }
}
