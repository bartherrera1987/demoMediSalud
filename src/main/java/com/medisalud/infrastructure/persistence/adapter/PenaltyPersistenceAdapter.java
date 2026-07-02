package com.medisalud.infrastructure.persistence.adapter;

import com.medisalud.application.ports.PenaltyRepositoryPort;
import com.medisalud.domain.model.Penalty;
import com.medisalud.infrastructure.persistence.mapper.PenaltyPersistenceMapper;
import com.medisalud.infrastructure.persistence.repository.PenaltyJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class PenaltyPersistenceAdapter implements PenaltyRepositoryPort {
    private final PenaltyJpaRepository repository;
    private final PenaltyPersistenceMapper mapper;

    public PenaltyPersistenceAdapter(PenaltyJpaRepository repository, PenaltyPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Penalty save(Penalty penalty) { return mapper.toDomain(repository.save(mapper.toEntity(penalty))); }
    public long countByPatientSince(UUID patientId, LocalDateTime since) { return repository.countByPatientIdAndCreatedAtAfter(patientId, since); }
}