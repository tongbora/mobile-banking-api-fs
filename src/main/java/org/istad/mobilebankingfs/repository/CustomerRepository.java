package org.istad.mobilebankingfs.repository;

import org.istad.mobilebankingfs.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    boolean existsByEmailAndPhoneNumber(String email, String phoneNumber);
    boolean existsByUuid(String uuid);
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    void deleteByEmail(String email);
    void deleteByUuid(String uuid);
}
