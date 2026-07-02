package com.medisalud.infrastructure.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Solicitud para registrar o actualizar un paciente")
public record PatientRequest(
        @Schema(description = "Nombre completo", example = "John Doe")
        @NotBlank @Size(max = 120) String name,
        @Schema(description = "Documento de identidad", example = "12345678")
        @NotBlank @Pattern(regexp = "^[A-Za-z0-9]{5,20}$") String document,
        @Schema(description = "Correo electrónico", example = "john.doe@email.com")
        @NotBlank @Email String email,
        @Schema(description = "Teléfono de contacto", example = "3001234567")
        @NotBlank @Pattern(regexp = "^[0-9+()\\-\\s]{7,20}$") String phone,
        @Schema(description = "Fecha de nacimiento", example = "1990-01-01")
        LocalDate birthDate) {
}