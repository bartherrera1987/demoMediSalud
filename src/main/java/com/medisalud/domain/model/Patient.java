package com.medisalud.domain.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public record Patient(UUID id, String name, Document document, Email email, Phone phone, LocalDate birthDate) {
    public Patient {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del paciente es obligatorio");
        }
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
        }
    }

    public int age() {
        return birthDate == null ? 0 : Period.between(birthDate, LocalDate.now()).getYears();
    }
}