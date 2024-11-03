package com.eduardo.moneysaver.core.application.controller.tags.dto;

import jakarta.validation.constraints.NotBlank;

public record NewTagDto(@NotBlank String descricao) {
}
