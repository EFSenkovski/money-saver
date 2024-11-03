package com.eduardo.moneysaver.core.application.controller.movimentos.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record NewMovimentoDto(@NotBlank String descricao, @PositiveOrZero Double valor,
                              @Pattern(regexp = "^[ES]$", message = "Campo precisa ser E ou S") String tipoMovimento,
                              @Positive Long contaId, @FutureOrPresent LocalDate dataEfetivacao,
                              @NotNull @NotEmpty List<@NotNull @Positive Long> tags) {
}
