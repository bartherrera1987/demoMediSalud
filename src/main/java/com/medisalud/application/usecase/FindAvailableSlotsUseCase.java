package com.medisalud.application.usecase;

import com.medisalud.application.dto.TimeSlotDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.service.AvailabilityCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class FindAvailableSlotsUseCase {
    private final AppointmentRepositoryPort appointments;
    private final AvailabilityCalculator calculator;
    private final DomainMapper mapper;

    public FindAvailableSlotsUseCase(AppointmentRepositoryPort appointments, AvailabilityCalculator calculator, DomainMapper mapper) {
        this.appointments = appointments;
        this.calculator = calculator;
        this.mapper = mapper;
    }

    public List<TimeSlotDto> execute(UUID doctorId, LocalDate date) {
        var busy = appointments.findBookedByDoctorAndRange(doctorId, date.atStartOfDay(), date.atTime(LocalTime.MAX));
        return calculator.calculate(date, busy).stream().map(mapper::toTimeSlotDto).toList();
    }
}