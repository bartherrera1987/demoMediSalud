package com.medisalud.domain.model;

public record Phone(String value) {
    public Phone {
        if (value == null || !value.matches("^[0-9+()\\-\\s]{7,20}$")) {
            throw new IllegalArgumentException("El teléfono no es válido");
        }
        value = value.trim();
    }
}