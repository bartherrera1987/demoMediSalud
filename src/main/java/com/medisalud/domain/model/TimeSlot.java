package com.medisalud.domain.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record TimeSlot(LocalDateTime start, LocalDateTime end) {
    public static final int MINUTES = 30;

    public TimeSlot {
        if (start == null || end == null || !end.equals(start.plusMinutes(MINUTES))) {
            throw new IllegalArgumentException("La cita debe durar exactamente 30 minutos");
        }
        if (start.getMinute() != 0 && start.getMinute() != 30) {
            throw new IllegalArgumentException("La cita debe iniciar en una franja de 30 minutos");
        }
    }

    public static TimeSlot of(LocalDateTime start) {
        return new TimeSlot(start, start.plusMinutes(MINUTES));
    }

    public LocalTime startTime() {
        return start.toLocalTime();
    }
}