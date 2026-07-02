package com.medisalud.domain.rules;

import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.exception.DoctorUnavailableException;

public class DoctorAvailabilityRule implements BusinessRule {
    private final AppointmentRepositoryPort appointments;

    public DoctorAvailabilityRule(AppointmentRepositoryPort appointments) {
        this.appointments = appointments;
    }

    @Override
    public void validate(BookingContext context) {
        if (appointments.existsBookedByDoctorAndStart(context.doctorId(), context.slot().start())) {
            throw new DoctorUnavailableException("El médico ya tiene una cita en esa franja");
        }
    }
}