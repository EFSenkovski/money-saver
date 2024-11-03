package com.eduardo.moneysaver.core.application.controller.contas.dto;

import com.eduardo.moneysaver.core.domain.model.Conta;

import java.time.LocalDateTime;

public record ContaResp(Long id, String nome, Double saldo, LocalDateTime criadoEm) {
    public ContaResp(Conta conta) {
        this(conta.getId(), conta.getNome(), conta.getSaldo(), conta.getCriadoEm());
    }
}
