package com.medisalud.domain.exception;

public class AppointmentNotFoundException extends BusinessException {
    public AppointmentNotFoundException(String message) {
        super("APPOINTMENT_NOT_FOUND", message);
    }
}