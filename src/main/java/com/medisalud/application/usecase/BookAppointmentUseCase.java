package com.medisalud.application.usecase;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.application.ports.DoctorRepositoryPort;
import com.medisalud.application.ports.PatientRepositoryPort;
import com.medisalud.domain.exception.NotFoundException;
import com.medisalud.domain.model.Appointment;
import com.medisalud.domain.model.TimeSlot;
import com.medisalud.domain.rules.BookingContext;
import com.medisalud.domain.service.BookingValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookAppointmentUseCase {
    private final DoctorRepositoryPort doctors;
    private final PatientRepositoryPort patients;
    private final AppointmentRepositoryPort appointments;
    private final BookingValidator validator;
    private final DomainMapper mapper;

    public BookAppointmentUseCase(DoctorRepositoryPort doctors, PatientRepositoryPort patients, AppointmentRepositoryPort appointments, BookingValidator validator, DomainMapper mapper) {
        this.doctors = doctors;
        this.patients = patients;
        this.appointments = appointments;
        this.validator = validator;
        this.mapper = mapper;
    }

    public AppointmentDto execute(UUID doctorId, UUID patientId, LocalDateTime start) {
        doctors.findById(doctorId).orElseThrow(() -> new NotFoundException("DOCTOR_NOT_FOUND", "Médico no encontrado"));
        var patient = patients.findById(patientId).orElseThrow(() -> new NotFoundException("PATIENT_NOT_FOUND", "Paciente no encontrado"));
        TimeSlot slot = TimeSlot.of(start);
        validator.validate(new BookingContext(doctorId, patient, slot));
        return mapper.toAppointmentDto(appointments.save(Appointment.book(doctorId, patientId, slot)));
    }
}