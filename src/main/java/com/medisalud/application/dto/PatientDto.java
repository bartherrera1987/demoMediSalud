package com.medisalud.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Datos del paciente")
public record PatientDto(
        @Schema(description = "Identificador único", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "Nombre completo", example = "John Doe")
        String name,
        @Schema(description = "Documento de identidad", example = "12345678")
        String document,
        @Schema(description = "Correo electrónico", example = "john.doe@email.com")
        String email,
        @Schema(description = "Teléfono de contacto", example = "3001234567")
        String phone,
        @Schema(description = "Fecha de nacimiento", example = "1990-01-01")
        LocalDate birthDate) {
}