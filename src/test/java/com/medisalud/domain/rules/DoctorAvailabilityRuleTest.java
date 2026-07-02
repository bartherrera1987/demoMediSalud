package com.medisalud.domain.rules;

import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.exception.DoctorUnavailableException;
import com.medisalud.domain.model.TimeSlot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DoctorAvailabilityRuleTest {
    private final AppointmentRepositoryPort appointments = mock(AppointmentRepositoryPort.class);
    private final DoctorAvailabilityRule rule = new DoctorAvailabilityRule(appointments);

    @Test
    @DisplayName("Debería fallar si el médico ya tiene una cita agendada en ese horario")
    void rejectsBusyDoctorSlot() {
        UUID doctorId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.parse("2026-07-02T09:00:00");
        when(appointments.existsBookedByDoctorAndStart(doctorId, start)).thenReturn(true);
        
        assertThrows(DoctorUnavailableException.class, 
            () -> rule.validate(new BookingContext(doctorId, null, TimeSlot.of(start))));
    }

    @Test
    @DisplayName("Debería pasar si el médico está disponible")
    void acceptsAvailableDoctor() {
        UUID doctorId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.parse("2026-07-02T10:00:00");
        when(appointments.existsBookedByDoctorAndStart(doctorId, start)).thenReturn(false);
        
        assertDoesNotThrow(() -> rule.validate(new BookingContext(doctorId, null, TimeSlot.of(start))));
    }
}