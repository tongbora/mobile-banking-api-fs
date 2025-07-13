package org.istad.mobilebankingfs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.istad.mobilebankingfs.dto.account.AccountRequest;
import org.istad.mobilebankingfs.dto.account.AccountUpdateRequest;
import org.istad.mobilebankingfs.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Account created successfully",
                        "account", accountService.createAccount(request)
                )
        );
    }


    @GetMapping
    public ResponseEntity<?> findAllAccounts() {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get all accounts successfully",
                        "account", accountService.findAllAccounts()
                )
        );
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<?> findByCustomerPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get account by customer phone number successfully",
                        "account", accountService.findByCustomerPhoneNumber(phoneNumber)
                )
        );
    }

    @DeleteMapping("/{actNo}")
    public ResponseEntity<?> deleteByActNo(@PathVariable String actNo) {
        accountService.deleteByActNo(actNo);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of(
                        "message", "Account deleted successfully"
                ));
    }

    @PutMapping("/{actNo}")
    public ResponseEntity<?> updateAccountByActNo(@PathVariable String actNo, @Valid @RequestBody AccountUpdateRequest request) {
        accountService.updateAccountByActNo(actNo, request);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Account updated successfully"
                )
        );
    }

    @PatchMapping("/{actNo}/disable")
    public ResponseEntity<?> disableAccountByActNo(@PathVariable String actNo) {
        accountService.disableAccountByActNo(actNo);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Account disabled successfully"
                )
        );
    }
}
