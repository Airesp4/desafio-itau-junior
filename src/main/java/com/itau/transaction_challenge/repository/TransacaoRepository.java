package com.itau.transaction_challenge.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itau.transaction_challenge.model.Transacao;

public class TransacaoRepository {
    
    private static TransacaoRepository instancia;
    private final List<Transacao> transacoes;

    private TransacaoRepository() {
        this.transacoes = new ArrayList<>();
    }

    public static TransacaoRepository getInstance() {
        if (instancia == null) {
            instancia = new TransacaoRepository();
        }
        return instancia;
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return Collections.unmodifiableList(transacoes);
    }

    public void limparTransacoes(){
        
        transacoes.clear();
    }
}
