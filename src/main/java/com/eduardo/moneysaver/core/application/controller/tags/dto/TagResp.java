package com.eduardo.moneysaver.core.application.controller.tags.dto;

import com.eduardo.moneysaver.core.domain.model.Tag;

import java.time.LocalDateTime;

public record TagResp(Long id, String descricao, LocalDateTime criadoEm) {
    public TagResp(Tag tag) {
        this(tag.getId(), tag.getDescricao(), tag.getCriadoEm());
    }
}
