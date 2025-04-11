package com.indigointelligence.stackademics.ProcessCounter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessCounterRepository extends JpaRepository<ProcessCounter, Long> {
    Optional<ProcessCounter> findByName(String name);
}
