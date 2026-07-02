package com.medisalud.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Penalty(UUID id, UUID patientId, UUID appointmentId, LocalDateTime createdAt, String reason) {
    private static final int CANCELLATION_THRESHOLD_HOURS = 2;

    public static Penalty lateCancellation(UUID patientId, UUID appointmentId, LocalDateTime createdAt) {
        return new Penalty(null, patientId, appointmentId, createdAt, "Cancelación con menos de dos horas de anticipación");
    }

    public static boolean isLateCancellation(LocalDateTime appointmentStart, LocalDateTime cancellationTime) {
        return java.time.Duration.between(cancellationTime, appointmentStart).toHours() < CANCELLATION_THRESHOLD_HOURS;
    }
}