package com.medisalud.domain.rules;

import com.medisalud.application.ports.PenaltyRepositoryPort;
import com.medisalud.domain.exception.PenaltyException;
import com.medisalud.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientPenaltyRuleTest {
    private final PenaltyRepositoryPort penalties = mock(PenaltyRepositoryPort.class);
    private final PatientPenaltyRule rule = new PatientPenaltyRule(penalties);

    @Test
    @DisplayName("Debería fallar si el paciente tiene tres o más penalizaciones recientes")
    void rejectsPatientWithThreeRecentPenalties() {
        UUID patientId = UUID.randomUUID();
        when(penalties.countByPatientSince(eq(patientId), any())).thenReturn(3L);
        Patient patient = new Patient(patientId, "Ana", new Document("CC12345"), new Email("ana@test.com"), new Phone("3001234567"), null);
        
        assertThrows(PenaltyException.class, 
            () -> rule.validate(new BookingContext(UUID.randomUUID(), patient, TimeSlot.of(LocalDateTime.parse("2026-07-02T09:00:00")))));
    }

    @Test
    @DisplayName("Debería pasar si el paciente tiene menos de tres penalizaciones")
    void acceptsPatientWithFewPenalties() {
        UUID patientId = UUID.randomUUID();
        when(penalties.countByPatientSince(eq(patientId), any())).thenReturn(2L);
        Patient patient = new Patient(patientId, "Ana", new Document("CC12345"), new Email("ana@test.com"), new Phone("3001234567"), null);
        
        assertDoesNotThrow(() -> rule.validate(new BookingContext(UUID.randomUUID(), patient, TimeSlot.of(LocalDateTime.parse("2026-07-02T09:00:00")))));
    }
}