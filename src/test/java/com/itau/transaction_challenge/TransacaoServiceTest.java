package com.itau.transaction_challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.itau.transaction_challenge.model.Transacao;
import com.itau.transaction_challenge.repository.TransacaoRepository;
import com.itau.transaction_challenge.service.TransacaoService;

public class TransacaoServiceTest {
    
    private TransacaoService transacaoService;
    private TransacaoRepository transacaoRepositoryMock;

    @BeforeEach
    void setUp() {
        transacaoService = new TransacaoService();
        transacaoRepositoryMock = mock(TransacaoRepository.class);
    }

    @Test
    void registrarTransacao_ComTransacaoValida_DeveAdicionarTransacao() {
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now());

        try (MockedStatic<TransacaoRepository> mockedRepository = Mockito.mockStatic(TransacaoRepository.class)) {
            mockedRepository.when(TransacaoRepository::getInstance).thenReturn(transacaoRepositoryMock);

            transacaoService.registrarTransacao(transacao);

            verify(transacaoRepositoryMock).adicionarTransacao(transacao);
        }
    }

    @Test
    void registrarTransacao_ComTransacaoNula_DeveLancarExcecao() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transacaoService.registrarTransacao(null);
        });

        assertEquals("Transação inválida!", exception.getMessage());
    }

    @Test
    void registrarTransacao_ComValorNegativo_DeveLancarExcecao() {
        Transacao transacao = new Transacao(-50.0, OffsetDateTime.now());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transacaoService.registrarTransacao(transacao);
        });

        assertEquals("Transação inválida!", exception.getMessage());
    }

    @Test
    void registrarTransacao_ComDataFutura_DeveLancarExcecao() {
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now().plusDays(1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transacaoService.registrarTransacao(transacao);
        });

        assertEquals("Transação futura não é permitida!", exception.getMessage());
    }

    @Test
    void deletarTransacoes_DeveLimparTransacoes() {
        try (MockedStatic<TransacaoRepository> mockedRepository = Mockito.mockStatic(TransacaoRepository.class)) {
            mockedRepository.when(TransacaoRepository::getInstance).thenReturn(transacaoRepositoryMock);

            transacaoService.deletarTransacoes();

            verify(transacaoRepositoryMock).limparTransacoes();
        }
    }
}
