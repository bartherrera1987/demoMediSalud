package com.medisalud.application.ports;

import com.medisalud.domain.model.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepositoryPort {
    Patient save(Patient patient);
    Optional<Patient> findById(UUID id);
    List<Patient> findAll();
    void deleteById(UUID id);
}