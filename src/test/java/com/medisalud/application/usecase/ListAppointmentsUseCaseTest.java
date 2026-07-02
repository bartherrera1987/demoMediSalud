package com.medisalud.application.usecase;

import com.medisalud.application.dto.AppointmentDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.model.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListAppointmentsUseCaseTest {

    @Mock
    private AppointmentRepositoryPort repository;

    @Mock
    private DomainMapper mapper;

    @InjectMocks
    private ListAppointmentsUseCase useCase;

    @Test
    void shouldListAppointments() {
        UUID doctorId = UUID.randomUUID();
        UUID patientId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Appointment appointment = mock(Appointment.class);
        AppointmentDto dto = new AppointmentDto(UUID.randomUUID(), doctorId, patientId, date.atTime(10, 0), date.atTime(10, 30), null);

        when(repository.findAll(doctorId, patientId, date)).thenReturn(List.of(appointment));
        when(mapper.toAppointmentDto(appointment)).thenReturn(dto);

        List<AppointmentDto> result = useCase.execute(doctorId, patientId, date);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}
