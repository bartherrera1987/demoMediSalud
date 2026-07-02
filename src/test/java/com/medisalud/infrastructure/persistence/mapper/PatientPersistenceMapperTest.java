package com.medisalud.infrastructure.persistence.mapper;

import com.medisalud.domain.model.*;
import com.medisalud.infrastructure.persistence.entity.PatientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PatientPersistenceMapperTest {
    private final PatientPersistenceMapper mapper = Mappers.getMapper(PatientPersistenceMapper.class);

    @Test
    @DisplayName("Debería mapear de Entidad a Dominio")
    void toDomain() {
        UUID id = UUID.randomUUID();
        PatientEntity entity = new PatientEntity();
        entity.setId(id);
        entity.setName("John Doe");
        entity.setDocument("12345678");
        entity.setEmail("john@test.com");
        entity.setPhone("3001234567");
        entity.setBirthDate(LocalDate.of(1990, 1, 1));

        Patient domain = mapper.toDomain(entity);

        assertEquals(id, domain.id());
        assertEquals("John Doe", domain.name());
        assertEquals("12345678", domain.document().value());
        assertEquals("john@test.com", domain.email().value());
        assertEquals("3001234567", domain.phone().value());
        assertEquals(LocalDate.of(1990, 1, 1), domain.birthDate());
    }

    @Test
    @DisplayName("Debería mapear de Dominio a Entidad")
    void toEntity() {
        UUID id = UUID.randomUUID();
        Patient domain = new Patient(id, "John Doe", new Document("12345678"), 
            new Email("john@test.com"), new Phone("3001234567"), LocalDate.of(1990, 1, 1));

        PatientEntity entity = mapper.toEntity(domain);

        assertEquals(id, entity.getId());
        assertEquals("John Doe", entity.getName());
        assertEquals("12345678", entity.getDocument());
        assertEquals("john@test.com", entity.getEmail());
        assertEquals("3001234567", entity.getPhone());
        assertEquals(LocalDate.of(1990, 1, 1), entity.getBirthDate());
    }
}
