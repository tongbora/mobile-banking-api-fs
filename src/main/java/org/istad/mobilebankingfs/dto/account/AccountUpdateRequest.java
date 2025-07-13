package org.istad.mobilebankingfs.dto.account;

import java.math.BigDecimal;

public record AccountUpdateRequest(
        BigDecimal balance
) {
}
