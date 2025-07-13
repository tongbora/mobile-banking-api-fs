package org.istad.mobilebankingfs.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record CustomerResponse(
        @NotBlank(message = "full name is required")
        String fullName,

        String uuid,

        @NotBlank(message = "gender is required")
        String gender,
        String email,
        String phoneNumber,
        String remark
) {
}
