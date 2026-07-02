package com.medisalud.infrastructure.persistence.repository;

import com.medisalud.infrastructure.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, UUID> {
}