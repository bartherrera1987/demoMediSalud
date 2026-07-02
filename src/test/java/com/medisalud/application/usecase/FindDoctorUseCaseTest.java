package com.medisalud.application.usecase;

import com.medisalud.application.dto.DoctorDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.DoctorRepositoryPort;
import com.medisalud.domain.exception.NotFoundException;
import com.medisalud.domain.model.Doctor;
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
class FindDoctorUseCaseTest {

    @Mock
    private DoctorRepositoryPort repository;

    @Mock
    private DomainMapper mapper;

    @InjectMocks
    private FindDoctorUseCase useCase;

    @Test
    void shouldFindDoctor() {
        UUID id = UUID.randomUUID();
        Doctor doctor = mock(Doctor.class);
        DoctorDto dto = new DoctorDto(id, "Dr. Test", "Test", "test@test.com", "1234567890");
        
        when(repository.findById(id)).thenReturn(Optional.of(doctor));
        when(mapper.toDoctorDto(doctor)).thenReturn(dto);

        DoctorDto result = useCase.execute(id);

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowExceptionWhenDoctorNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(id));
    }
}
