package org.istad.mobilebankingfs.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        String username,
        String email,
        String firstName,
        String lastName,
//        @NotBlank(message = "Password cannot be null")
        String password,
//        @NotBlank(message = "Confirmed password cannot be null")
        String confirmedPassword
) {
}


