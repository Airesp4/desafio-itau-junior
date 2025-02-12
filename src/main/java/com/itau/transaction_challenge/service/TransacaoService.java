package com.itau.transaction_challenge.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.itau.transaction_challenge.model.Transacao;
import com.itau.transaction_challenge.repository.TransacaoRepository;

@Service
public class TransacaoService {
    
    public void registrarTransacao(Transacao transacao){

        if (transacao == null || transacao.getValor() < 0 || transacao.getDataHora() == null) {

            throw new IllegalArgumentException("Transação inválida!");
        }

        if (transacao.getDataHora().isAfter(OffsetDateTime.now())) {

            throw new IllegalArgumentException("Transação futura não é permitida!");
        }

        TransacaoRepository.getInstance().adicionarTransacao(transacao);
    }
}
