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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterPatientUseCaseTest {

    @Mock
    private PatientRepositoryPort repository;

    @Mock
    private DomainMapper mapper;

    @InjectMocks
    private RegisterPatientUseCase useCase;

    @Test
    void shouldRegisterPatient() {
        PatientDto dto = new PatientDto(null, "Test Patient", "12345678", "test@test.com", "1234567890", null);
        Patient patient = mock(Patient.class);
        
        when(mapper.toPatient(dto)).thenReturn(patient);
        when(repository.save(patient)).thenReturn(patient);
        when(mapper.toPatientDto(patient)).thenReturn(dto);

        PatientDto result = useCase.execute(dto);

        assertEquals(dto, result);
        verify(repository).save(patient);
    }
}
