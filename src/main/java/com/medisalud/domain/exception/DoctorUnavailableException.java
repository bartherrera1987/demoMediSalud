package com.medisalud.domain.exception;

public class DoctorUnavailableException extends BusinessException {
    public DoctorUnavailableException(String message) {
        super("DOCTOR_UNAVAILABLE", message);
    }
}