package com.eduardo.moneysaver.core.application.controller.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthDto(@Email String email, @NotBlank String password) {
}
