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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class TransacaoController {
    
    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private EstatisticaService estatisticaService;

    @PostMapping("/transacao")
    @Operation(summary = "Registra uma transação", description = "Valida e registra uma transação com informações de valor e data.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transação registrada!"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos!"),
        @ApiResponse(responseCode = "422", description = "Erro ao registar transação.")
    })
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
    @Operation(summary = "Deleta transações", description = "Apaga todos os registros de transações salvos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registros de transações deletados!")
    })
    public ResponseEntity<Void> limparTransacoes(){

        transacaoService.deletarTransacoes();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    @Operation(summary = "Gera estatísticas", description = "Disponibiliza dados de estatísticas de transações realizadas nos últimos 60 segundos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informações de estatísticas gerados com sucesso!")
    })
    public ResponseEntity<EstatisticaDTO> calcularEstatisticas(){

        EstatisticaDTO estatistica = estatisticaService.geraEstatistica();

        return ResponseEntity.ok().body(estatistica);
    }
}
