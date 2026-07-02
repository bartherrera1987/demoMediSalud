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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListDoctorsUseCaseTest {

    @Mock
    private DoctorRepositoryPort repository;

    @Mock
    private DomainMapper mapper;

    @InjectMocks
    private ListDoctorsUseCase useCase;

    @Test
    void shouldListDoctors() {
        Doctor doctor = mock(Doctor.class);
        DoctorDto dto = new DoctorDto(UUID.randomUUID(), "Dr. Test", "Test", "test@test.com", "1234567890");
        
        when(repository.findAll()).thenReturn(List.of(doctor));
        when(mapper.toDoctorDto(doctor)).thenReturn(dto);

        List<DoctorDto> result = useCase.execute();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}
