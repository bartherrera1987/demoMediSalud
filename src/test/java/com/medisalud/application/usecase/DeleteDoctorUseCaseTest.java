package com.medisalud.application.usecase;

import com.medisalud.application.ports.DoctorRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteDoctorUseCaseTest {

    @Mock
    private DoctorRepositoryPort repository;

    @InjectMocks
    private DeleteDoctorUseCase useCase;

    @Test
    void shouldDeleteDoctor() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(java.util.Optional.of(org.mockito.Mockito.mock(com.medisalud.domain.model.Doctor.class)));
        useCase.execute(id);
        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenDoctorNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());
        org.junit.jupiter.api.Assertions.assertThrows(com.medisalud.domain.exception.NotFoundException.class, () -> useCase.execute(id));
    }
}
