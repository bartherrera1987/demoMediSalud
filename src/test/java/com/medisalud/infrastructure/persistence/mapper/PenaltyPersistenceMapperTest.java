package com.medisalud.infrastructure.persistence.mapper;

import com.medisalud.domain.model.Penalty;
import com.medisalud.infrastructure.persistence.entity.PenaltyEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PenaltyPersistenceMapperTest {
    private final PenaltyPersistenceMapper mapper = Mappers.getMapper(PenaltyPersistenceMapper.class);

    @Test
    @DisplayName("Debería mapear de Entidad a Dominio")
    void toDomain() {
        UUID id = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        
        PenaltyEntity entity = new PenaltyEntity();
        entity.setId(id);
        entity.setPatientId(patientId);
        entity.setCreatedAt(now);
        entity.setReason("NO_SHOW");

        Penalty domain = mapper.toDomain(entity);

        assertEquals(id, domain.id());
        assertEquals(patientId, domain.patientId());
        assertEquals(now, domain.createdAt());
        assertEquals("NO_SHOW", domain.reason());
    }

    @Test
    @DisplayName("Debería mapear de Dominio a Entidad")
    void toEntity() {
        UUID id = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        
        Penalty domain = new Penalty(id, patientId, UUID.randomUUID(), now, "CANCELLATION");

        PenaltyEntity entity = mapper.toEntity(domain);

        assertEquals(id, entity.getId());
        assertEquals(patientId, entity.getPatientId());
        assertEquals(now, entity.getCreatedAt());
        assertEquals("CANCELLATION", entity.getReason());
    }
}
