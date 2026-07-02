package com.medisalud.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Datos del médico")
public record DoctorDto(
        @Schema(description = "Identificador único", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "Nombre completo", example = "Dr. Gregory House")
        String name,
        @Schema(description = "Especialidad médica", example = "Diagnóstico")
        String specialty,
        @Schema(description = "Correo electrónico", example = "house@medisalud.com")
        String email,
        @Schema(description = "Teléfono de contacto", example = "3001234567")
        String phone) {
}