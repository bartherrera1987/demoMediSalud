package com.medisalud.application.usecase;

import com.medisalud.application.dto.PatientDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.PatientRepositoryPort;
import com.medisalud.domain.exception.NotFoundException;
import com.medisalud.domain.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindPatientUseCaseTest {

    @Mock
    private PatientRepositoryPort repository;

    @Mock
    private DomainMapper mapper;

    @InjectMocks
    private FindPatientUseCase useCase;

    @Test
    void shouldFindPatient() {
        UUID id = UUID.randomUUID();
        Patient patient = mock(Patient.class);
        PatientDto dto = new PatientDto(id, "Test Patient", "12345678", "test@test.com", "1234567890", null);
        
        when(repository.findById(id)).thenReturn(Optional.of(patient));
        when(mapper.toPatientDto(patient)).thenReturn(dto);

        PatientDto result = useCase.execute(id);

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(id));
    }
}
