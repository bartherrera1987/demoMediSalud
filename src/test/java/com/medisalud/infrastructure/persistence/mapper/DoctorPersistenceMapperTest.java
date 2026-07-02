package com.medisalud.infrastructure.persistence.mapper;

import com.medisalud.domain.model.*;
import com.medisalud.infrastructure.persistence.entity.DoctorEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DoctorPersistenceMapperTest {
    private final DoctorPersistenceMapper mapper = Mappers.getMapper(DoctorPersistenceMapper.class);

    @Test
    @DisplayName("Debería mapear de Entidad a Dominio")
    void toDomain() {
        UUID id = UUID.randomUUID();
        DoctorEntity entity = new DoctorEntity();
        entity.setId(id);
        entity.setName("Dr. House");
        entity.setSpecialty("Diagnóstico");
        entity.setEmail("house@test.com");
        entity.setPhone("3001234567");

        Doctor domain = mapper.toDomain(entity);

        assertEquals(id, domain.id());
        assertEquals("Dr. House", domain.name());
        assertEquals("Diagnóstico", domain.specialty());
        assertEquals("house@test.com", domain.email().value());
        assertEquals("3001234567", domain.phone().value());
    }

    @Test
    @DisplayName("Debería mapear de Dominio a Entidad")
    void toEntity() {
        UUID id = UUID.randomUUID();
        Doctor domain = new Doctor(id, "Dr. House", "Diagnóstico", 
            new Email("house@test.com"), new Phone("3001234567"));

        DoctorEntity entity = mapper.toEntity(domain);

        assertEquals(id, entity.getId());
        assertEquals("Dr. House", entity.getName());
        assertEquals("Diagnóstico", entity.getSpecialty());
        assertEquals("house@test.com", entity.getEmail());
        assertEquals("3001234567", entity.getPhone());
    }
}
