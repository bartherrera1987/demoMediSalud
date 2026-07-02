package com.medisalud.domain.exception;

public class WorkingHoursException extends BusinessException {
    public WorkingHoursException(String message) {
        super("WORKING_HOURS_VIOLATION", message);
    }
}