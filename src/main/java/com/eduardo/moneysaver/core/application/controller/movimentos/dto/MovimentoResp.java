package com.eduardo.moneysaver.core.application.controller.movimentos.dto;

import com.eduardo.moneysaver.core.application.controller.contas.dto.ContaResp;
import com.eduardo.moneysaver.core.application.controller.tags.dto.TagResp;
import com.eduardo.moneysaver.core.domain.model.Movimento;
import com.eduardo.moneysaver.core.domain.model.StatusMovimento;
import com.eduardo.moneysaver.core.domain.model.Tag;
import com.eduardo.moneysaver.core.domain.model.TipoMovimento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record MovimentoResp(Long id, String descricao, Double valor, TipoMovimento tipoMovimento, ContaForMovimentoResp conta,
                            LocalDate dataEfetivacao, LocalDateTime criadoEm, Set<String> tags, StatusMovimento statusMovimento) {
    public MovimentoResp(Movimento movimento) {
        this(movimento.getId(),
                movimento.getDescricao(),
                movimento.getValor(),
                movimento.getTipo(),
                new ContaForMovimentoResp(movimento.getConta()),
                movimento.getDataEfetivacao(),
                movimento.getCriadoEm(),
                movimento.getTags().stream()
                        .map(Tag::getDescricao)
                        .collect(Collectors.toSet()),
                movimento.getStatus()
        );
    }
}
