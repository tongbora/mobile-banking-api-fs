package org.istad.mobilebankingfs.service.impl;

import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.domain.KYC;
import org.istad.mobilebankingfs.dto.customer.KYCResponse;
import org.istad.mobilebankingfs.mapper.CustomerMapper;
import org.istad.mobilebankingfs.repository.KYCRepository;
import org.istad.mobilebankingfs.service.KYCService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class KYCServiceImpl implements KYCService {
    private final KYCRepository kycRepository;
    private final CustomerMapper customerMapper;

    @Override
    public KYCResponse verifyKYC(String nationalCardId) {
        KYC kyc = kycRepository.findByNationalCardId(nationalCardId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "National Card ID not found."
                        )
                );
        if(kyc.getIsVerified().equals(true)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "KYC already verified."
            );
        }
        kyc.setIsVerified(true);
        return customerMapper.toKYCResponse(kycRepository.save(kyc));
    }
}
