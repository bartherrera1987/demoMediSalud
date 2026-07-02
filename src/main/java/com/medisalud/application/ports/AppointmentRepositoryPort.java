package com.medisalud.application.ports;

import com.medisalud.domain.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepositoryPort {
    Appointment save(Appointment appointment);
    Optional<Appointment> findById(UUID id);
    List<Appointment> findBookedByDoctorAndRange(UUID doctorId, LocalDateTime from, LocalDateTime to);
    boolean existsBookedByDoctorAndStart(UUID doctorId, LocalDateTime start);
    boolean existsBookedByDoctorPatientAndStart(UUID doctorId, UUID patientId, LocalDateTime start);
    List<Appointment> findAll(UUID doctorId, UUID patientId, LocalDate date);
}