package com.medisalud.application.usecase;

import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.application.ports.PenaltyRepositoryPort;
import com.medisalud.domain.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.*;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CancelAppointmentUseCaseTest {
    private final DomainMapper mapper = Mappers.getMapper(DomainMapper.class);

    @Test
    void createsPenaltyWhenCancellationIsLate() {
        AppointmentRepositoryPort appointments = mock(AppointmentRepositoryPort.class);
        PenaltyRepositoryPort penalties = mock(PenaltyRepositoryPort.class);
        Clock clock = Clock.fixed(Instant.parse("2026-07-02T08:00:00Z"), ZoneOffset.UTC);
        UUID appointmentId = UUID.randomUUID();
        Appointment appointment = new Appointment(appointmentId, UUID.randomUUID(), UUID.randomUUID(), TimeSlot.of(LocalDateTime.parse("2026-07-02T09:30:00")), AppointmentStatus.BOOKED, LocalDateTime.parse("2026-07-01T09:00:00"), null);
        when(appointments.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointments.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = new CancelAppointmentUseCase(appointments, penalties, clock, mapper).execute(appointmentId);

        assertEquals(AppointmentStatus.CANCELLED, result.status());
        verify(penalties).save(any());
    }
}