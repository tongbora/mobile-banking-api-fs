package org.istad.mobilebankingfs.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.istad.mobilebankingfs.domain.Account;
import org.istad.mobilebankingfs.domain.Customer;
import org.istad.mobilebankingfs.dto.account.AccountRequest;
import org.istad.mobilebankingfs.dto.account.AccountResponse;
import org.istad.mobilebankingfs.dto.account.AccountUpdateRequest;
import org.istad.mobilebankingfs.mapper.AccountMapper;
import org.istad.mobilebankingfs.repository.AccountRepository;
import org.istad.mobilebankingfs.repository.AccountTypeRepository;
import org.istad.mobilebankingfs.repository.CustomerRepository;
import org.istad.mobilebankingfs.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountTypeRepository accountTypeRepository;

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        Customer customer = customerRepository.findByPhoneNumber(request.customerPhoneNumber()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found.")
        );
        Account account = new Account();

        // user can have 1 account type
        // This uses to check if user has this kind of account or not
        if(accountRepository.existsByAccountType(accountTypeRepository.findByName(request.accountType()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type not found.")
        )) && accountRepository.existsByCustomer(customer)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account already exists.");
        }

        account.setAccountType(accountTypeRepository.findByName(request.accountType().toLowerCase()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account type not found.")
        ));
        // check account number will not be conflicted
        String actNo;
        do {
            actNo = String.format("%09d", new Random().nextInt(1_000_000_000));
        } while (accountRepository.existsByActNo(actNo));
        account.setActNo(actNo);

        account.setActCurrency(request.actCurrency());
        account.setCustomer(customer);
        Account acc = accountRepository.save(account);
        return accountMapper.toAccountResponse(acc);
    }

    @Override
    public List<AccountResponse> findAllAccounts() {
        return accountRepository.findAll().stream()
                .filter(account -> !account.getIsDeleted())
                .map(accountMapper::toAccountResponse).toList();
    }

    @Override
    public AccountResponse findByActNo(String actNo) {
        return accountMapper.toAccountResponse(
                accountRepository.findByActNo(actNo).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found.")
                )
        );
    }

    @Override
    public AccountResponse findByCustomerPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found.")
        );
        return accountMapper.toAccountResponse(
                accountRepository.findByCustomer(customer).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found.")
                )
        );
    }

    @Override
    @Transactional
    public void deleteByActNo(String actNo) {
        if(!accountRepository.existsByActNo(actNo)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Account not found.");
        }
        accountRepository.deleteByActNo(actNo);
    }

    @Override
//    @Transactional
    public void updateAccountByActNo(String actNo, AccountUpdateRequest request) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found.")
        );
//        accountMapper.updateAccount(account, request);
        account.setBalance(request.balance());
        log.info("Account updated successfully: {}", account);
        accountRepository.save(account);
    }

    @Override
    public void disableAccountByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found.")
        );
        account.setIsDeleted(true);
        accountRepository.save(account);
    }
}
