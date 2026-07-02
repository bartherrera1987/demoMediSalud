package com.medisalud.application.usecase;

import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.PatientRepositoryPort;
import com.medisalud.domain.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListPatientsUseCaseTest {

    @Mock
    private PatientRepositoryPort repository;

    @Mock
    private DomainMapper mapper;

    @InjectMocks
    private ListPatientsUseCase useCase;

    @Test
    void shouldListPatients() {
        Patient patient = mock(Patient.class);
        PatientDto dto = new PatientDto(UUID.randomUUID(), "Test Patient", "12345678", "test@test.com", "1234567890", null);
        
        when(repository.findAll()).thenReturn(List.of(patient));
        when(mapper.toPatientDto(patient)).thenReturn(dto);

        List<PatientDto> result = useCase.execute();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}
