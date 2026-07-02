package com.medisalud.domain.rules;

import com.medisalud.domain.exception.BusinessException;

public class PatientAgeRule implements BusinessRule {
    @Override
    public void validate(BookingContext context) {
        if (context.patient().age() < 0) {
            throw new BusinessException("INVALID_PATIENT_AGE", "La edad del paciente no es válida");
        }
    }
}