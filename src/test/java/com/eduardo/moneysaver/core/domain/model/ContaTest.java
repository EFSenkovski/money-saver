package com.eduardo.moneysaver.core.domain.model;

import com.eduardo.moneysaver.core.application.exception.error.SaldoInsuficienteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    @Test
    void shouldReturn200WhenEntradaDe100EmContaComSaldo100() {
        var conta = Conta.newConta()
                .saldo(100.0)
                .build();
        conta.updateSaldo(100.0, TipoMovimento.E);
        Assertions.assertEquals(conta.getSaldo(), 200.0);
    }

    @Test
    void shouldReturn0WhenSaidaDe100EmContaComSaldo100() {
        var conta = Conta.newConta()
                .saldo(100.0)
                .build();
        conta.updateSaldo(100.0, TipoMovimento.S);
        Assertions.assertEquals(conta.getSaldo(), 0);
    }

    @Test
    void shouldReturnErrorWhenSaidaDe100EmContaComSaldo50() {
        Exception exception = assertThrows(SaldoInsuficienteException.class, () -> {
            var conta = Conta.newConta()
                    .saldo(50.0)
                    .build();
            conta.updateSaldo(100.0, TipoMovimento.S);
        });
        assertTrue(exception.getMessage().contains("Saldo insuficiente para efetuar a movimentação"));
    }

    @Test
    void shouldReturnErrorWhenSaidaDe10EmContaComSaldo2() {
        Exception exception = assertThrows(SaldoInsuficienteException.class, () -> {
            var conta = Conta.newConta()
                    .saldo(2.0)
                    .build();
            conta.updateSaldo(10.0, TipoMovimento.S);
        });
        assertTrue(exception.getMessage().contains("Saldo insuficiente para efetuar a movimentação"));
    }
}