package com.medisalud.infrastructure.persistence.mapper;

import com.medisalud.domain.model.*;
import com.medisalud.infrastructure.persistence.entity.AppointmentEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentPersistenceMapperTest {
    private final AppointmentPersistenceMapper mapper = Mappers.getMapper(AppointmentPersistenceMapper.class);

    @Test
    @DisplayName("Debería mapear de Entidad a Dominio")
    void toDomain() {
        UUID id = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusMinutes(30);
        
        AppointmentEntity entity = new AppointmentEntity();
        entity.setId(id);
        entity.setDoctorId(doctorId);
        entity.setPatientId(patientId);
        entity.setStartAt(start);
        entity.setEndAt(end);
        entity.setStatus(AppointmentStatus.BOOKED);
        entity.setCreatedAt(start.minusHours(1));

        Appointment domain = mapper.toDomain(entity);

        assertEquals(id, domain.id());
        assertEquals(doctorId, domain.doctorId());
        assertEquals(patientId, domain.patientId());
        assertEquals(start, domain.slot().start());
        assertEquals(end, domain.slot().end());
        assertEquals(AppointmentStatus.BOOKED, domain.status());
    }

    @Test
    @DisplayName("Debería mapear de Dominio a Entidad")
    void toEntity() {
        UUID id = UUID.randomUUID();
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        
        Appointment domain = new Appointment(id, doctorId, patientId, 
            TimeSlot.of(start), AppointmentStatus.BOOKED, start.minusHours(1), null);

        AppointmentEntity entity = mapper.toEntity(domain);

        assertEquals(id, entity.getId());
        assertEquals(doctorId, entity.getDoctorId());
        assertEquals(patientId, entity.getPatientId());
        assertEquals(start, entity.getStartAt());
        assertEquals(AppointmentStatus.BOOKED, entity.getStatus());
    }
}
