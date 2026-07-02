package com.medisalud.application.ports;

import com.medisalud.domain.model.Penalty;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PenaltyRepositoryPort {
    Penalty save(Penalty penalty);
    long countByPatientSince(UUID patientId, LocalDateTime since);
}