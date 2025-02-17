package com.itau.transaction_challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.itau.transaction_challenge.DTO.EstatisticaDTO;
import com.itau.transaction_challenge.model.Transacao;
import com.itau.transaction_challenge.repository.TransacaoRepository;
import com.itau.transaction_challenge.service.EstatisticaService;

public class EstatisticaServiceTest {
    private EstatisticaService estatisticaService;
    private TransacaoRepository transacaoRepositoryMock;

    @BeforeEach
    void setUp() {
        estatisticaService = new EstatisticaService();
        transacaoRepositoryMock = mock(TransacaoRepository.class);
    }

    @Test
    void filtraTransacoes_DeveRetornarApenasTransacoesDosUltimos60Segundos() {
        Transacao transacao1 = new Transacao(100.0, OffsetDateTime.now().minusSeconds(30));
        Transacao transacao2 = new Transacao(200.0, OffsetDateTime.now().minusSeconds(70));
        Transacao transacao3 = new Transacao(300.0, OffsetDateTime.now().minusSeconds(10));

        try (MockedStatic<TransacaoRepository> mockedRepository = mockStatic(TransacaoRepository.class)) {
            mockedRepository.when(TransacaoRepository::getInstance).thenReturn(transacaoRepositoryMock);
            when(transacaoRepositoryMock.listarTransacoes()).thenReturn(List.of(transacao1, transacao2, transacao3));

            List<Transacao> resultado = estatisticaService.filtraTransacoes();

            assertEquals(2, resultado.size());
            assertEquals(100.0, resultado.get(0).getValor());
            assertEquals(300.0, resultado.get(1).getValor());
        }
    }

    @Test
    void geraEstatistica_DeveCalcularEstatisticasCorretamente() {
        Transacao transacao1 = new Transacao(100.0, OffsetDateTime.now().minusSeconds(30));
        Transacao transacao2 = new Transacao(200.0, OffsetDateTime.now().minusSeconds(10));

        try (MockedStatic<TransacaoRepository> mockedRepository = mockStatic(TransacaoRepository.class)) {
            mockedRepository.when(TransacaoRepository::getInstance).thenReturn(transacaoRepositoryMock);
            when(transacaoRepositoryMock.listarTransacoes()).thenReturn(List.of(transacao1, transacao2));

            EstatisticaDTO estatistica = estatisticaService.geraEstatistica();

            assertEquals(2, estatistica.count());
            assertEquals(300.0, estatistica.sum());
            assertEquals(150.0, estatistica.avg());
            assertEquals(100.0, estatistica.min());
            assertEquals(200.0, estatistica.max());
        }
    }

    @Test
    void geraEstatistica_SemTransacoesNosUltimos60Segundos_DeveRetornarZeros() {
        try (MockedStatic<TransacaoRepository> mockedRepository = mockStatic(TransacaoRepository.class)) {
            mockedRepository.when(TransacaoRepository::getInstance).thenReturn(transacaoRepositoryMock);
            when(transacaoRepositoryMock.listarTransacoes()).thenReturn(List.of());

            EstatisticaDTO estatistica = estatisticaService.geraEstatistica();

            assertEquals(0, estatistica.count());
            assertEquals(0.0, estatistica.sum());
            assertEquals(0.0, estatistica.avg());
            assertEquals(0.0, estatistica.min());
            assertEquals(0.0, estatistica.max());
        }
    }
}
