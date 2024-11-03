package com.eduardo.moneysaver.core.application.controller.contas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record NewContaDto(@NotBlank String nome, @NotNull @PositiveOrZero Double saldo) {
}
