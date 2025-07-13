package org.istad.mobilebankingfs.dto.customer;

public record CustomerUpdate(
        String fullName,
        String email,
        String phoneNumber,
        String remark
) {
}
