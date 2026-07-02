package com.medisalud.application.usecase;

import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.DoctorRepositoryPort;
import com.medisalud.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindDoctorUseCase {
    private final DoctorRepositoryPort repository;
    private final DomainMapper mapper;

    public FindDoctorUseCase(DoctorRepositoryPort repository, DomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public DoctorDto execute(UUID id) {
        return repository.findById(id)
                .map(mapper::toDoctorDto)
                .orElseThrow(() -> new NotFoundException("DOCTOR_NOT_FOUND", "Médico no encontrado"));
    }
}
