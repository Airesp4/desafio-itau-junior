package com.itau.transaction_challenge.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.itau.transaction_challenge.DTO.EstatisticaDTO;
import com.itau.transaction_challenge.model.Transacao;
import com.itau.transaction_challenge.repository.TransacaoRepository;

@Service
public class EstatisticaService {
    
    private final Logger logger = LoggerFactory.getLogger(EstatisticaService.class);

    @Value("${app.config.tempo-filtro-segundos}")
    private int tempoFiltroSegundos;

    public List<Transacao> filtraTransacoes(){

        var estatisticas = TransacaoRepository.getInstance().listarTransacoes();

        logger.info("Transações antes da filtragem: {}", estatisticas);

        OffsetDateTime dateTimeNow = OffsetDateTime.now();

        return estatisticas.stream()
                .filter(transacao -> Duration.between(transacao.getDataHora(), dateTimeNow).getSeconds() <= tempoFiltroSegundos)
                .collect(Collectors.toList());
    }
    
    public EstatisticaDTO geraEstatistica(){                
        long startTime = System.nanoTime();

        List<Transacao> transacoesFiltradas = filtraTransacoes();

        logger.info("Filtrando transações: {}", transacoesFiltradas);

        int count = transacoesFiltradas.size();
        double sum = transacoesFiltradas.stream()
            .mapToDouble(Transacao::getValor)
            .sum();

        double avg = sum/count;

        if (count == 0) {
            avg = 0.0;
        }
        
        double min = transacoesFiltradas.stream()
            .mapToDouble(Transacao::getValor)
            .min()
            .orElse(0.0);
        
        double max = transacoesFiltradas.stream()
            .mapToDouble(Transacao::getValor)
            .max()
            .orElse(0.0);
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        
        
        logger.info("Gerando estatísticas - Contagem: {}, Soma: {}, Média: {}, Mínimo: {}, Máximo: {}", 
            count, sum, avg, min, max);
        
        logger.info("As estatísticas foram geradas em {} ms", duration);
        
        return new EstatisticaDTO(count, sum, avg, min, max);
    }
}
