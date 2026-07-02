package com.medisalud.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Franja horaria disponible")
public record TimeSlotDto(
        @Schema(description = "Fecha y hora de inicio", example = "2024-07-03T10:00:00")
        LocalDateTime start,
        @Schema(description = "Fecha y hora de fin", example = "2024-07-03T10:30:00")
        LocalDateTime end) {
}