package com.medisalud.application.ports;

import com.medisalud.domain.model.Doctor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorRepositoryPort {
    Doctor save(Doctor doctor);
    Optional<Doctor> findById(UUID id);
    List<Doctor> findAll();
    void deleteById(UUID id);
}