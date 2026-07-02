package com.medisalud.application.usecase;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.exception.AppointmentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RescheduleAppointmentUseCase {
    private final AppointmentRepositoryPort appointments;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final BookAppointmentUseCase bookAppointmentUseCase;

    public RescheduleAppointmentUseCase(AppointmentRepositoryPort appointments, CancelAppointmentUseCase cancelAppointmentUseCase, BookAppointmentUseCase bookAppointmentUseCase) {
        this.appointments = appointments;
        this.cancelAppointmentUseCase = cancelAppointmentUseCase;
        this.bookAppointmentUseCase = bookAppointmentUseCase;
    }

    @Transactional
    public AppointmentDto execute(UUID appointmentId, LocalDateTime newStart) {
        var current = appointments.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada"));
        cancelAppointmentUseCase.execute(appointmentId);
        return bookAppointmentUseCase.execute(current.doctorId(), current.patientId(), newStart);
    }
}