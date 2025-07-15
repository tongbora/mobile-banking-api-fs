package org.istad.mobilebankingfs.dto.customer;

public record KYCResponse(
        String nationalCardId,
        Boolean isVerified
) {
}
