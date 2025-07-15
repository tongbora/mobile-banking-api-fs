package org.istad.mobilebankingfs.repository;

import org.istad.mobilebankingfs.domain.KYC;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KYCRepository
        extends CrudRepository<KYC, Integer> {
    Optional<KYC> findByNationalCardId(String nationalCardId);
    boolean existsByNationalCardId(String nationalCardId);
}
