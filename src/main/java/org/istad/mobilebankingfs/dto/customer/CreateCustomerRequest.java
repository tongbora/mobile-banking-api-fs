package org.istad.mobilebankingfs.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
        @NotBlank(message = "full name is required")
        String fullName,

        @NotBlank(message = "national card id is required")
        String nationalCardId,
        @NotBlank(message = "gender is required")
        String gender,
        String email,
        String phoneNumber,
        String segment,
        String remark
) {
}
