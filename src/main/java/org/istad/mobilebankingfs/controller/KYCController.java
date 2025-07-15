package org.istad.mobilebankingfs.controller;


import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.dto.customer.KYCResponse;
import org.istad.mobilebankingfs.service.KYCService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers/kyc")
@RequiredArgsConstructor
public class KYCController {
    private final KYCService kycService;

    @PutMapping("/verify/{nationalCardId}")
    public ResponseEntity<Map<String,Object>> verifyKYC(@PathVariable String nationalCardId) {
        return ResponseEntity.ok(Map.of(
                "message", "KYC verified successfully",
                "kyc", kycService.verifyKYC(nationalCardId)
        ));
    }
}
