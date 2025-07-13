package org.istad.mobilebankingfs.repository;

import org.istad.mobilebankingfs.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    Optional<AccountType> findByName(String name);
}
