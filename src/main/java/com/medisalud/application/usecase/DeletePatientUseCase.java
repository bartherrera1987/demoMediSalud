package com.medisalud.application.usecase;

import com.medisalud.application.ports.PatientRepositoryPort;
import com.medisalud.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeletePatientUseCase {
    private final PatientRepositoryPort repository;

    public DeletePatientUseCase(PatientRepositoryPort repository) {
        this.repository = repository;
    }

    @Transactional
    public void execute(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("PATIENT_NOT_FOUND", "Paciente no encontrado");
        }
        repository.deleteById(id);
    }
}
