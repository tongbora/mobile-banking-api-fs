package org.istad.mobilebankingfs.repository;

import org.istad.mobilebankingfs.domain.KYC;
import org.springframework.data.repository.CrudRepository;

public interface KYCRepository
        extends CrudRepository<KYC, Integer> {
}
