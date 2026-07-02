package com.medisalud.domain.rules;

import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.exception.PatientConflictException;

public class PatientConflictRule implements BusinessRule {
    private final AppointmentRepositoryPort appointments;

    public PatientConflictRule(AppointmentRepositoryPort appointments) {
        this.appointments = appointments;
    }

    @Override
    public void validate(BookingContext context) {
        if (appointments.existsBookedByDoctorPatientAndStart(context.doctorId(), context.patient().id(), context.slot().start())) {
            throw new PatientConflictException("El paciente ya tiene cita con el médico en esa franja");
        }
    }
}