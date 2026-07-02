package com.medisalud.application.dto;

import com.medisalud.domain.model.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Datos de la cita médica")
public record AppointmentDto(
        @Schema(description = "Identificador único", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "ID del médico", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID doctorId,
        @Schema(description = "ID del paciente", example = "550e8400-e29b-41d4-a716-446655440002")
        UUID patientId,
        @Schema(description = "Fecha y hora de inicio", example = "2024-07-03T10:00:00")
        LocalDateTime start,
        @Schema(description = "Fecha y hora de fin", example = "2024-07-03T10:30:00")
        LocalDateTime end,
        @Schema(description = "Estado de la cita", example = "BOOKED")
        AppointmentStatus status) {
}