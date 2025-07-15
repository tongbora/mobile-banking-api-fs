package org.istad.mobilebankingfs.repository;

import org.istad.mobilebankingfs.domain.Segment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SegmentRepository
        extends JpaRepository<Segment, Integer> {

    Optional<Segment> findByName(String name);
}
