package com.medisalud.application.usecase;

import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.*;
import com.medisalud.domain.exception.NotFoundException;
import com.medisalud.domain.model.*;
import com.medisalud.domain.rules.BusinessRule;
import com.medisalud.domain.service.BookingValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookAppointmentUseCaseTest {
    private final DoctorRepositoryPort doctors = mock(DoctorRepositoryPort.class);
    private final PatientRepositoryPort patients = mock(PatientRepositoryPort.class);
    private final AppointmentRepositoryPort appointments = mock(AppointmentRepositoryPort.class);
    private final BusinessRule rule = mock(BusinessRule.class);
    private final BookingValidator validator = new BookingValidator(List.of(rule));
    private final DomainMapper mapper = Mappers.getMapper(DomainMapper.class);
    private final BookAppointmentUseCase useCase = new BookAppointmentUseCase(doctors, patients, appointments, validator, mapper);

    @Test
    @DisplayName("Debería agendar una cita después de validar las reglas de negocio")
    void booksAppointmentAfterExecutingRules() {
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.parse("2026-07-02T09:00:00");
        when(doctors.findById(doctorId)).thenReturn(Optional.of(new Doctor(doctorId, "Dr Test", "General", new Email("dr@test.com"), new Phone("3001234567"))));
        when(patients.findById(patientId)).thenReturn(Optional.of(new Patient(patientId, "Ana", new Document("CC12345"), new Email("ana@test.com"), new Phone("3001234567"), null)));
        when(appointments.save(any())).thenAnswer(invocation -> {
            Appointment appointment = invocation.getArgument(0);
            return new Appointment(UUID.randomUUID(), appointment.doctorId(), appointment.patientId(), appointment.slot(), appointment.status(), appointment.createdAt(), appointment.cancelledAt());
        });

        var result = useCase.execute(doctorId, patientId, start);

        assertEquals(doctorId, result.doctorId());
        assertEquals(patientId, result.patientId());
        verify(rule).validate(any());
        verify(appointments).save(any());
    }

    @Test
    @DisplayName("Debería fallar si el médico no existe")
    void rejectsMissingDoctor() {
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        when(doctors.findById(doctorId)).thenReturn(Optional.empty());
        
        assertThrows(NotFoundException.class, () -> useCase.execute(doctorId, patientId, LocalDateTime.parse("2026-07-02T09:00:00")));
    }

    @Test
    @DisplayName("Debería fallar si el paciente no existe")
    void rejectsMissingPatient() {
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        when(doctors.findById(doctorId)).thenReturn(Optional.of(mock(Doctor.class)));
        when(patients.findById(patientId)).thenReturn(Optional.empty());
        
        assertThrows(NotFoundException.class, () -> useCase.execute(doctorId, patientId, LocalDateTime.parse("2026-07-02T09:00:00")));
    }
}