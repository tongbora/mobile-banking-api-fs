package org.istad.mobilebankingfs.repository;

import org.istad.mobilebankingfs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   Optional< User> findByUsername(String username);
}
