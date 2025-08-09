package org.istad.mobilebankingfs.dto.auth;

import lombok.Builder;

@Builder
public record RegisterResponse(
    String email,
    String firstName,
    String lastName
) {
}
