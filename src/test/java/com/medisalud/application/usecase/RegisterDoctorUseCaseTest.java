package com.medisalud.application.usecase;

import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.DoctorRepositoryPort;
import com.medisalud.domain.model.Doctor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterDoctorUseCaseTest {

    @Mock
    private DoctorRepositoryPort repository;

    @Mock
    private DomainMapper mapper;

    @InjectMocks
    private RegisterDoctorUseCase useCase;

    @Test
    void shouldRegisterDoctor() {
        DoctorDto dto = new DoctorDto(null, "Dr. Test", "Test", "test@test.com", "1234567890");
        Doctor doctor = mock(Doctor.class);
        
        when(mapper.toDoctor(dto)).thenReturn(doctor);
        when(repository.save(doctor)).thenReturn(doctor);
        when(mapper.toDoctorDto(doctor)).thenReturn(dto);

        DoctorDto result = useCase.execute(dto);

        assertEquals(dto, result);
        verify(repository).save(doctor);
    }
}
