package com.medisalud.domain.rules;

import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.exception.PatientConflictException;
import com.medisalud.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientConflictRuleTest {
    private final AppointmentRepositoryPort appointments = mock(AppointmentRepositoryPort.class);
    private final PatientConflictRule rule = new PatientConflictRule(appointments);

    @Test
    @DisplayName("Debería fallar si el paciente ya tiene una cita con el mismo médico en ese horario")
    void rejectsSameDoctorSameSlotForPatient() {
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.parse("2026-07-02T09:00:00");
        when(appointments.existsBookedByDoctorPatientAndStart(doctorId, patientId, start)).thenReturn(true);
        Patient patient = new Patient(patientId, "Ana", new Document("CC12345"), new Email("ana@test.com"), new Phone("3001234567"), null);
        
        assertThrows(PatientConflictException.class, 
            () -> rule.validate(new BookingContext(doctorId, patient, TimeSlot.of(start))));
    }

    @Test
    @DisplayName("Debería pasar si el paciente no tiene citas en ese horario")
    void acceptsAvailablePatient() {
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.parse("2026-07-02T10:00:00");
        when(appointments.existsBookedByDoctorPatientAndStart(doctorId, patientId, start)).thenReturn(false);
        Patient patient = new Patient(patientId, "Ana", new Document("CC12345"), new Email("ana@test.com"), new Phone("3001234567"), null);
        
        assertDoesNotThrow(() -> rule.validate(new BookingContext(doctorId, patient, TimeSlot.of(start))));
    }
}