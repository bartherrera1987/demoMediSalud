package com.medisalud.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Appointment(UUID id, UUID doctorId, UUID patientId, TimeSlot slot, AppointmentStatus status, LocalDateTime createdAt, LocalDateTime cancelledAt) {
    public static Appointment book(UUID doctorId, UUID patientId, TimeSlot slot) {
        return new Appointment(null, doctorId, patientId, slot, AppointmentStatus.BOOKED, LocalDateTime.now(), null);
    }

    public Appointment cancel(LocalDateTime now) {
        if (!isBooked()) {
            throw new IllegalStateException("Solo se pueden cancelar citas agendadas");
        }
        return new Appointment(id, doctorId, patientId, slot, AppointmentStatus.CANCELLED, createdAt, now);
    }

    public boolean isBooked() {
        return status == AppointmentStatus.BOOKED;
    }

    public boolean isCancelled() {
        return status == AppointmentStatus.CANCELLED;
    }
}