package org.istad.mobilebankingfs.dto.account;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountUpdateRequest(
        @NotNull(message = "Balance cannot be null")
        @DecimalMin(value = "0.0", inclusive = true, message = "Balance must be greater than or equal to 0")
        BigDecimal balance
) {
}
