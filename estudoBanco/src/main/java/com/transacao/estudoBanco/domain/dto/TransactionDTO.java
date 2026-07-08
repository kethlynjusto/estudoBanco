package com.transacao.estudoBanco.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private BigDecimal value;
    private Long payer;
    private Long payee;

}
