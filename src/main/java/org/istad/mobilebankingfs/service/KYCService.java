package org.istad.mobilebankingfs.service;

import org.istad.mobilebankingfs.dto.customer.KYCResponse;

public interface KYCService {
    KYCResponse verifyKYC(String nationalCardId);
}
