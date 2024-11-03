package com.eduardo.moneysaver.core.application.controller.auth.dto;

import com.eduardo.moneysaver.core.domain.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserDto(@NotBlank @Email String email, @NotBlank String password, @NotNull UserRole role) {
}
