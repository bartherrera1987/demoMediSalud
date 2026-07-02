package com.medisalud.domain.model;

import java.util.UUID;

public record Doctor(UUID id, String name, String specialty, Email email, Phone phone) {
    public Doctor {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del médico es obligatorio");
        }
        if (specialty == null || specialty.isBlank()) {
            throw new IllegalArgumentException("La especialidad es obligatoria");
        }
    }
}