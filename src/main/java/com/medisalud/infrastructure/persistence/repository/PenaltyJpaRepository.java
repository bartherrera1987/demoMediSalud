package com.medisalud.infrastructure.persistence.repository;

import com.medisalud.infrastructure.persistence.entity.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PenaltyJpaRepository extends JpaRepository<PenaltyEntity, UUID> {
    long countByPatientIdAndCreatedAtAfter(UUID patientId, LocalDateTime since);
}