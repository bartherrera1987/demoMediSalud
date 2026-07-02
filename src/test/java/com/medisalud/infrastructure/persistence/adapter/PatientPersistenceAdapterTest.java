package com.medisalud.infrastructure.persistence.adapter;

import com.medisalud.domain.model.*;
import com.medisalud.infrastructure.persistence.entity.PatientEntity;
import com.medisalud.infrastructure.persistence.mapper.PatientPersistenceMapper;
import com.medisalud.infrastructure.persistence.repository.PatientJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientPersistenceAdapterTest {

    @Mock
    private PatientJpaRepository repository;

    @Mock
    private PatientPersistenceMapper mapper;

    @InjectMocks
    private PatientPersistenceAdapter adapter;

    @Test
    @DisplayName("Debería guardar un paciente")
    void save() {
        Patient patient = new Patient(null, "John Doe", new Document("CC12345"), 
            new Email("john@test.com"), new Phone("3001234567"), LocalDate.now());
        PatientEntity entity = new PatientEntity();
        when(mapper.toEntity(patient)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(patient);

        Patient result = adapter.save(patient);

        assertNotNull(result);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debería buscar por ID")
    void findById() {
        UUID id = UUID.randomUUID();
        PatientEntity entity = new PatientEntity();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(mock(Patient.class));

        Optional<Patient> result = adapter.findById(id);

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Debería buscar todos")
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(new PatientEntity()));
        when(mapper.toDomain(any())).thenReturn(mock(Patient.class));

        List<Patient> result = adapter.findAll();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debería eliminar por ID")
    void deleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        adapter.deleteById(id);

        verify(repository).deleteById(id);
    }
}
