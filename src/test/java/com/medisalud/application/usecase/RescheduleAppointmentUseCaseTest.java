package com.medisalud.application.usecase;

import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.exception.AppointmentNotFoundException;
import com.medisalud.domain.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RescheduleAppointmentUseCaseTest {
    private final AppointmentRepositoryPort appointments = mock(AppointmentRepositoryPort.class);
    private final CancelAppointmentUseCase cancel = mock(CancelAppointmentUseCase.class);
    private final BookAppointmentUseCase book = mock(BookAppointmentUseCase.class);
    private final RescheduleAppointmentUseCase useCase = new RescheduleAppointmentUseCase(appointments, cancel, book);

    @Test
    @DisplayName("Debería cancelar la cita actual y agendar una nueva al reprogramar")
    void rescheduleCancelsAndBooksNewAppointment() {
        UUID appointmentId = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        Appointment current = new Appointment(appointmentId, doctorId, patientId, 
            TimeSlot.of(LocalDateTime.parse("2026-07-02T09:00:00")), AppointmentStatus.BOOKED, LocalDateTime.now(), null);
        LocalDateTime newStart = LocalDateTime.parse("2026-07-03T10:00:00");
        when(appointments.findById(appointmentId)).thenReturn(Optional.of(current));

        useCase.execute(appointmentId, newStart);

        verify(cancel).execute(appointmentId);
        verify(book).execute(doctorId, patientId, newStart);
    }

    @Test
    @DisplayName("Debería fallar si la cita a reprogramar no existe")
    void rejectsMissingAppointment() {
        UUID appointmentId = UUID.randomUUID();
        when(appointments.findById(appointmentId)).thenReturn(Optional.empty());
        
        assertThrows(AppointmentNotFoundException.class, () -> useCase.execute(appointmentId, LocalDateTime.now().plusDays(1)));
    }
}