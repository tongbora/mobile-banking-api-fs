package org.istad.mobilebankingfs.service;

import org.istad.mobilebankingfs.domain.Customer;
import org.istad.mobilebankingfs.dto.account.AccountRequest;
import org.istad.mobilebankingfs.dto.account.AccountResponse;
import org.istad.mobilebankingfs.dto.account.AccountUpdateRequest;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
    List<AccountResponse> findAllAccounts();
    AccountResponse findByActNo(String actNo);
    AccountResponse findByCustomerPhoneNumber(String phoneNumber);
    void deleteByActNo(String actNo);
    void updateAccountByActNo(String actNo, AccountUpdateRequest request);
    void disableAccountByActNo(String actNo);
}
