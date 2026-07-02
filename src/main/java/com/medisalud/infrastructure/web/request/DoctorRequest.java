package com.medisalud.infrastructure.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para registrar o actualizar un médico")
public record DoctorRequest(
        @Schema(description = "Nombre completo", example = "Dr. Gregory House")
        @NotBlank @Size(max = 120) String name,
        @Schema(description = "Especialidad médica", example = "Diagnóstico")
        @NotBlank @Size(max = 80) String specialty,
        @Schema(description = "Correo electrónico", example = "house@medisalud.com")
        @NotBlank @Email String email,
        @Schema(description = "Teléfono de contacto", example = "3001234567")
        @NotBlank @Pattern(regexp = "^[0-9+()\\-\\s]{7,20}$") String phone) {
}