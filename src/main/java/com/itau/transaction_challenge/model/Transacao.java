package com.itau.transaction_challenge.model;

import java.time.OffsetDateTime;

import com.itau.transaction_challenge.DTO.TransacaoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    
    private double valor;
    private OffsetDateTime dataHora;

    public Transacao(TransacaoDTO dto) {
        this.valor = dto.valor();
        this.dataHora = dto.dataHora();
    }
}
