package com.eduardo.moneysaver.core.domain.model;

import com.eduardo.moneysaver.core.application.exception.error.MovimentoJaEfetivadoException;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovimentoTest {
    @Test
    void shouldReturnEWhenEfetivadaMovimentacaoP() {
        var movimento = Movimento.newMovimento()
                .valor(50.0)
                .tipo(TipoMovimento.E)
                .conta(Conta.newConta()
                        .saldo(100.0)
                        .build())
                .status(StatusMovimento.P)
                .build();

        movimento.efetivar();
        Assertions.assertEquals(StatusMovimento.E, movimento.getStatus());
    }

    @Test
    void shouldReturnErrorWhenEfetivadaMovimentacaoE() {
        Exception exception = assertThrows(MovimentoJaEfetivadoException.class, () -> {
            var movimento = Movimento.newMovimento()
                    .valor(50.0)
                    .tipo(TipoMovimento.E)
                    .conta(Conta.newConta()
                            .saldo(100.0)
                            .build())
                    .status(StatusMovimento.E)
                    .build();
            movimento.efetivar();
        });
        assertTrue(exception.getMessage().contains("Movimentação já efetivada"));
    }
}