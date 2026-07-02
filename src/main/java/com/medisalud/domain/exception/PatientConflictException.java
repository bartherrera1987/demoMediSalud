package com.medisalud.domain.exception;

public class PatientConflictException extends BusinessException {
    public PatientConflictException(String message) {
        super("PATIENT_CONFLICT", message);
    }
}