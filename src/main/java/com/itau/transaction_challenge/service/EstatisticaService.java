package com.itau.transaction_challenge.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itau.transaction_challenge.DTO.EstatisticaDTO;
import com.itau.transaction_challenge.model.Transacao;
import com.itau.transaction_challenge.repository.TransacaoRepository;

@Service
public class EstatisticaService {
    
    public List<Transacao> filtraTransacoes(){
        var estatisticas = TransacaoRepository.getInstance().listarTransacoes();

        OffsetDateTime dateTimeNow = OffsetDateTime.now();

        return estatisticas.stream()
                .filter(transacao -> Duration.between(transacao.getDataHora(), dateTimeNow).getSeconds() <= 60)
                .collect(Collectors.toList());
    }
    public EstatisticaDTO pegaEstatistica(){                
        
        List<Transacao> transacoesFiltradas = filtraTransacoes();

        int count = transacoesFiltradas.size();
        double sum = transacoesFiltradas.stream()
            .mapToDouble(Transacao::getValor)
            .sum();

        double avg = sum/count;

        double min = transacoesFiltradas.stream()
            .mapToDouble(Transacao::getValor)
            .min()
            .orElse(0.0);
        
        double max = transacoesFiltradas.stream()
            .mapToDouble(Transacao::getValor)
            .max()
            .orElse(0.0);
        
        return new EstatisticaDTO(count, sum, avg, min, max);
    }
}
