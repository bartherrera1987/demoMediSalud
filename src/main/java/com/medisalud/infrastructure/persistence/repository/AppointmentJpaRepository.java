package com.medisalud.infrastructure.persistence.repository;

import com.medisalud.domain.model.AppointmentStatus;
import com.medisalud.infrastructure.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, UUID> {
    boolean existsByDoctorIdAndStartAtAndStatus(UUID doctorId, LocalDateTime startAt, AppointmentStatus status);
    boolean existsByDoctorIdAndPatientIdAndStartAtAndStatus(UUID doctorId, UUID patientId, LocalDateTime startAt, AppointmentStatus status);
    List<AppointmentEntity> findByDoctorIdAndStatusAndStartAtBetween(UUID doctorId, AppointmentStatus status, LocalDateTime from, LocalDateTime to);

    @Query("select a from AppointmentEntity a where (:doctorId is null or a.doctorId = :doctorId) and (:patientId is null or a.patientId = :patientId) and (:from is null or a.startAt >= :from) and (:to is null or a.startAt <= :to)")
    List<AppointmentEntity> search(@Param("doctorId") UUID doctorId, @Param("patientId") UUID patientId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}