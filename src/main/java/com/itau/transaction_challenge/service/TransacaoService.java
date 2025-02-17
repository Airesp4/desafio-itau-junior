package com.itau.transaction_challenge.service;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.itau.transaction_challenge.model.Transacao;
import com.itau.transaction_challenge.repository.TransacaoRepository;

@Service
public class TransacaoService {
    
    private final Logger logger = LoggerFactory.getLogger(TransacaoService.class);
    
    public void registrarTransacao(Transacao transacao){

        if (transacao == null || transacao.getValor() < 0 || transacao.getDataHora() == null) {
            
            logger.error("Tentativa de registrar transação inválida: {}", transacao);
            throw new IllegalArgumentException("Transação inválida!");
        }
        
        if (transacao.getDataHora().isAfter(OffsetDateTime.now())) {

            logger.error("Tentativa de registrar transação futura: {}", transacao);
            throw new IllegalArgumentException("Transação futura não é permitida!");
        }        

        logger.info("Registrando transação: {}", transacao);
        TransacaoRepository.getInstance().adicionarTransacao(transacao);
    }

    public void deletarTransacoes(){

        logger.info("Limpando transações...");
        TransacaoRepository.getInstance().limparTransacoes();
    }
}
