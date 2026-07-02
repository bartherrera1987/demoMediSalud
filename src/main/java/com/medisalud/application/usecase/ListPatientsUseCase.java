package com.medisalud.application.usecase;

import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.PatientRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListPatientsUseCase {
    private final PatientRepositoryPort repository;
    private final DomainMapper mapper;

    public ListPatientsUseCase(PatientRepositoryPort repository, DomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PatientDto> execute() {
        return repository.findAll().stream()
                .map(mapper::toPatientDto)
                .toList();
    }
}
