package com.medisalud.infrastructure.persistence.adapter;

import com.medisalud.domain.model.Penalty;
import com.medisalud.infrastructure.persistence.entity.PenaltyEntity;
import com.medisalud.infrastructure.persistence.mapper.PenaltyPersistenceMapper;
import com.medisalud.infrastructure.persistence.repository.PenaltyJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PenaltyPersistenceAdapterTest {

    @Mock
    private PenaltyJpaRepository repository;

    @Mock
    private PenaltyPersistenceMapper mapper;

    @InjectMocks
    private PenaltyPersistenceAdapter adapter;

    @Test
    @DisplayName("Debería guardar una penalización")
    void save() {
        Penalty penalty = new Penalty(null, UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), "TEST");
        PenaltyEntity entity = new PenaltyEntity();
        when(mapper.toEntity(penalty)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(penalty);

        Penalty result = adapter.save(penalty);

        assertNotNull(result);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Debería contar penalizaciones por paciente desde fecha")
    void countByPatientSince() {
        UUID patientId = UUID.randomUUID();
        LocalDateTime since = LocalDateTime.now();
        when(repository.countByPatientIdAndCreatedAtAfter(patientId, since)).thenReturn(5L);

        long count = adapter.countByPatientSince(patientId, since);

        assertEquals(5L, count);
    }
}
