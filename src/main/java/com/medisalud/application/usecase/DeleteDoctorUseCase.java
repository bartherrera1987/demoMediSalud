package com.medisalud.application.usecase;

import com.medisalud.application.ports.DoctorRepositoryPort;
import com.medisalud.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteDoctorUseCase {
    private final DoctorRepositoryPort repository;

    public DeleteDoctorUseCase(DoctorRepositoryPort repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("DOCTOR_NOT_FOUND", "Médico no encontrado");
        }
        repository.deleteById(id);
    }
}
