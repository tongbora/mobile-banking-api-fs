package org.istad.mobilebankingfs.repository;

import org.istad.mobilebankingfs.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository
        extends JpaRepository<Role, Integer> {
}
