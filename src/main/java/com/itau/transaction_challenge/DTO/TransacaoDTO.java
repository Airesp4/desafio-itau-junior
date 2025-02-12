package com.itau.transaction_challenge.DTO;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TransacaoDTO(
        @PositiveOrZero(message = "O valor da transação deve ser maior ou igual a zero")
        double valor,

        @NotNull(message = "A data e hora da transação não pode ser nula")
        OffsetDateTime dataHora
) {
}
