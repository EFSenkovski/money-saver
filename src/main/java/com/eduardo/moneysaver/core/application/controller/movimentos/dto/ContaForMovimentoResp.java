package com.eduardo.moneysaver.core.application.controller.movimentos.dto;

import com.eduardo.moneysaver.core.domain.model.Conta;

public record ContaForMovimentoResp(Long id, String nome) {
    public ContaForMovimentoResp(Conta conta) {
        this(conta.getId(), conta.getNome());
    }
}
