package com.medisalud.domain.model;

public record Document(String value) {
    public Document {
        if (value == null || !value.matches("^[A-Za-z0-9]{5,20}$")) {
            throw new IllegalArgumentException("El documento no es válido");
        }
        value = value.trim().toUpperCase();
    }
}