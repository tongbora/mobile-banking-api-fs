package org.istad.mobilebankingfs.dto.account;

import jakarta.validation.constraints.NotBlank;

public record AccountRequest(
        String actCurrency,
        @NotBlank(message = "phone number is required")
        String customerPhoneNumber,
        @NotBlank(message = "account type is required")
        String accountType
) {
}
