package com.medisalud.application.usecase;

import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.PatientRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterPatientUseCase {
    private final PatientRepositoryPort repository;
    private final DomainMapper mapper;

    public RegisterPatientUseCase(PatientRepositoryPort repository, DomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public PatientDto execute(PatientDto dto) {
        return mapper.toPatientDto(repository.save(mapper.toPatient(dto)));
    }
}
