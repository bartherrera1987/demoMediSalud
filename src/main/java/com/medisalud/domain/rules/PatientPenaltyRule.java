package com.medisalud.domain.rules;

import com.medisalud.application.ports.PenaltyRepositoryPort;
import com.medisalud.domain.exception.PenaltyException;

public class PatientPenaltyRule implements BusinessRule {
    private final PenaltyRepositoryPort penalties;

    public PatientPenaltyRule(PenaltyRepositoryPort penalties) {
        this.penalties = penalties;
    }

    @Override
    public void validate(BookingContext context) {
        if (penalties.countByPatientSince(context.patient().id(), context.slot().start().minusDays(30)) >= 3) {
            throw new PenaltyException("El paciente tiene tres penalizaciones en los últimos treinta días");
        }
    }
}