package com.medisalud.infrastructure.persistence.adapter;

import com.medisalud.domain.model.*;
import com.medisalud.infrastructure.persistence.entity.DoctorEntity;
import com.medisalud.infrastructure.persistence.mapper.DoctorPersistenceMapper;
import com.medisalud.infrastructure.persistence.repository.DoctorJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorPersistenceAdapterTest {

    @Mock
    private DoctorJpaRepository repository;

    @Mock
    private DoctorPersistenceMapper mapper;

    @InjectMocks
    private DoctorPersistenceAdapter adapter;

    @Test
    @DisplayName("Debería guardar un médico")
    void save() {
        Doctor doctor = new Doctor(null, "Dr. House", "Diagnóstico", new Email("house@test.com"), new Phone("3001234567"));
        DoctorEntity entity = new DoctorEntity();
        when(mapper.toEntity(doctor)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(doctor);

        Doctor result = adapter.save(doctor);

        assertNotNull(result);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debería buscar por ID")
    void findById() {
        UUID id = UUID.randomUUID();
        DoctorEntity entity = new DoctorEntity();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(mock(Doctor.class));

        Optional<Doctor> result = adapter.findById(id);

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Debería buscar todos")
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(new DoctorEntity()));
        when(mapper.toDomain(any())).thenReturn(mock(Doctor.class));

        List<Doctor> result = adapter.findAll();

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
