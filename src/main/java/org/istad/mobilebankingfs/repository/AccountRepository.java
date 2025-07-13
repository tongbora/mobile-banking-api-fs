package org.istad.mobilebankingfs.repository;

import org.istad.mobilebankingfs.domain.Account;
import org.istad.mobilebankingfs.domain.AccountType;
import org.istad.mobilebankingfs.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository
        extends JpaRepository<Account, Integer>{
    Boolean existsByActNo(String actNo);
    Boolean existsByAccountType(AccountType accountType);
    Boolean existsByCustomer(Customer customer);

    Optional<Account> findByActNo(String actNo);
    Optional<Account> findByCustomer(Customer customer);
    void deleteByActNo(String actNo);
}
