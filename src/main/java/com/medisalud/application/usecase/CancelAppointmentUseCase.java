package com.medisalud.application.usecase;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.application.ports.PenaltyRepositoryPort;
import com.medisalud.domain.exception.AppointmentNotFoundException;
import com.medisalud.domain.model.Penalty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CancelAppointmentUseCase {
    private final AppointmentRepositoryPort appointments;
    private final PenaltyRepositoryPort penalties;
    private final Clock clock;
    private final DomainMapper mapper;

    public CancelAppointmentUseCase(AppointmentRepositoryPort appointments, PenaltyRepositoryPort penalties, Clock clock, DomainMapper mapper) {
        this.appointments = appointments;
        this.penalties = penalties;
        this.clock = clock;
        this.mapper = mapper;
    }

    @Transactional
    public AppointmentDto execute(UUID appointmentId) {
        var appointment = appointments.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Cita no encontrada"));
        LocalDateTime now = LocalDateTime.now(clock);
        
        if (appointment.isBooked() && Penalty.isLateCancellation(appointment.slot().start(), now)) {
            penalties.save(Penalty.lateCancellation(appointment.patientId(), appointment.id(), now));
        }
        
        return mapper.toAppointmentDto(appointments.save(appointment.cancel(now)));
    }
}