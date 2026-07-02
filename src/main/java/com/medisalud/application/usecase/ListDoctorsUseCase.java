package com.medisalud.application.usecase;

import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.DoctorRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListDoctorsUseCase {
    private final DoctorRepositoryPort repository;
    private final DomainMapper mapper;

    public ListDoctorsUseCase(DoctorRepositoryPort repository, DomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<DoctorDto> execute() {
        return repository.findAll().stream()
                .map(mapper::toDoctorDto)
                .toList();
    }
}
