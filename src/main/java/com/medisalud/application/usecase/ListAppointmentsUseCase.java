package com.medisalud.application.usecase;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ListAppointmentsUseCase {
    private final AppointmentRepositoryPort appointments;
    private final DomainMapper mapper;

    public ListAppointmentsUseCase(AppointmentRepositoryPort appointments, DomainMapper mapper) {
        this.appointments = appointments;
        this.mapper = mapper;
    }

    public List<AppointmentDto> execute(UUID doctorId, UUID patientId, LocalDate date) {
        return appointments.findAll(doctorId, patientId, date).stream().map(mapper::toAppointmentDto).toList();
    }
}