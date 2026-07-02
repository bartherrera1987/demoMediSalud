package com.medisalud.domain.exception;

public class PenaltyException extends BusinessException {
    public PenaltyException(String message) {
        super("PATIENT_PENALIZED", message);
    }
}