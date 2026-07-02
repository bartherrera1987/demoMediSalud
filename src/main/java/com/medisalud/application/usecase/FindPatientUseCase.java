package com.medisalud.application.usecase;

import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.PatientRepositoryPort;
import com.medisalud.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindPatientUseCase {
    private final PatientRepositoryPort repository;
    private final DomainMapper mapper;

    public FindPatientUseCase(PatientRepositoryPort repository, DomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PatientDto execute(UUID id) {
        return repository.findById(id)
                .map(mapper::toPatientDto)
                .orElseThrow(() -> new NotFoundException("PATIENT_NOT_FOUND", "Paciente no encontrado"));
    }
}
