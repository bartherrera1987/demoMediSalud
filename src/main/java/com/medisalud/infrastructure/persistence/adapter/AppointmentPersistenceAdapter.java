package com.medisalud.infrastructure.persistence.adapter;

import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.model.Appointment;
import com.medisalud.domain.model.AppointmentStatus;
import com.medisalud.infrastructure.persistence.mapper.AppointmentPersistenceMapper;
import com.medisalud.infrastructure.persistence.repository.AppointmentJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AppointmentPersistenceAdapter implements AppointmentRepositoryPort {
    private final AppointmentJpaRepository repository;
    private final AppointmentPersistenceMapper mapper;

    public AppointmentPersistenceAdapter(AppointmentJpaRepository repository, AppointmentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Appointment save(Appointment appointment) { return mapper.toDomain(repository.save(mapper.toEntity(appointment))); }
    public Optional<Appointment> findById(UUID id) { return repository.findById(id).map(mapper::toDomain); }
    public List<Appointment> findBookedByDoctorAndRange(UUID doctorId, LocalDateTime from, LocalDateTime to) { return repository.findByDoctorIdAndStatusAndStartAtBetween(doctorId, AppointmentStatus.BOOKED, from, to).stream().map(mapper::toDomain).toList(); }
    public boolean existsBookedByDoctorAndStart(UUID doctorId, LocalDateTime start) { return repository.existsByDoctorIdAndStartAtAndStatus(doctorId, start, AppointmentStatus.BOOKED); }
    public boolean existsBookedByDoctorPatientAndStart(UUID doctorId, UUID patientId, LocalDateTime start) { return repository.existsByDoctorIdAndPatientIdAndStartAtAndStatus(doctorId, patientId, start, AppointmentStatus.BOOKED); }
    public List<Appointment> findAll(UUID doctorId, UUID patientId, LocalDate date) {
        LocalDateTime from = date == null ? null : date.atStartOfDay();
        LocalDateTime to = date == null ? null : date.atTime(23, 59, 59);
        return repository.search(doctorId, patientId, from, to).stream().map(mapper::toDomain).toList();
    }
}