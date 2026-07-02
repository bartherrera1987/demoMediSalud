package com.medisalud.application.usecase;

import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.DoctorRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterDoctorUseCase {
    private final DoctorRepositoryPort repository;
    private final DomainMapper mapper;

    public RegisterDoctorUseCase(DoctorRepositoryPort repository, DomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public DoctorDto execute(DoctorDto dto) {
        return mapper.toDoctorDto(repository.save(mapper.toDoctor(dto)));
    }
}
