package com.medisalud.infrastructure.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Solicitud para agendar una nueva cita")
public record BookAppointmentRequest(
        @Schema(description = "ID del médico", example = "550e8400-e29b-41d4-a716-446655440001")
        @NotNull UUID doctorId,
        @Schema(description = "ID del paciente", example = "550e8400-e29b-41d4-a716-446655440002")
        @NotNull UUID patientId,
        @Schema(description = "Fecha y hora de inicio de la cita (debe ser futura)", example = "2024-07-03T10:00:00")
        @NotNull @Future LocalDateTime start) {
}