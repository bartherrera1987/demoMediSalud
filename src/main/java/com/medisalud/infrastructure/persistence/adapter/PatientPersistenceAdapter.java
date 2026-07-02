package com.medisalud.infrastructure.persistence.adapter;

import com.medisalud.application.ports.PatientRepositoryPort;
import com.medisalud.domain.model.Patient;
import com.medisalud.infrastructure.persistence.mapper.PatientPersistenceMapper;
import com.medisalud.infrastructure.persistence.repository.PatientJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PatientPersistenceAdapter implements PatientRepositoryPort {
    private final PatientJpaRepository repository;
    private final PatientPersistenceMapper mapper;

    public PatientPersistenceAdapter(PatientJpaRepository repository, PatientPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Patient save(Patient patient) { return mapper.toDomain(repository.save(mapper.toEntity(patient))); }
    public Optional<Patient> findById(UUID id) { return repository.findById(id).map(mapper::toDomain); }
    public List<Patient> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}