package com.medisalud.infrastructure.persistence.adapter;

import com.medisalud.application.ports.DoctorRepositoryPort;
import com.medisalud.domain.model.Doctor;
import com.medisalud.infrastructure.persistence.mapper.DoctorPersistenceMapper;
import com.medisalud.infrastructure.persistence.repository.DoctorJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DoctorPersistenceAdapter implements DoctorRepositoryPort {
    private final DoctorJpaRepository repository;
    private final DoctorPersistenceMapper mapper;

    public DoctorPersistenceAdapter(DoctorJpaRepository repository, DoctorPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Doctor save(Doctor doctor) { return mapper.toDomain(repository.save(mapper.toEntity(doctor))); }
    public Optional<Doctor> findById(UUID id) { return repository.findById(id).map(mapper::toDomain); }
    public List<Doctor> findAll() { return repository.findAll().stream().map(mapper::toDomain).toList(); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}