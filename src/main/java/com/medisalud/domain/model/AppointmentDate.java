package com.medisalud.domain.model;

import java.time.LocalDate;

public record AppointmentDate(LocalDate value) {
    public AppointmentDate {
        if (value == null) {
            throw new IllegalArgumentException("La fecha de la cita es obligatoria");
        }
    }
}