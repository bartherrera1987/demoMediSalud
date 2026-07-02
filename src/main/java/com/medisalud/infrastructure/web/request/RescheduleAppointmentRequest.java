package com.medisalud.infrastructure.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Solicitud para reprogramar una cita")
public record RescheduleAppointmentRequest(
        @Schema(description = "Nueva fecha y hora de inicio (debe ser futura)", example = "2024-07-04T11:00:00")
        @NotNull @Future LocalDateTime start) {
}