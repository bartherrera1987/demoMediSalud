package com.medisalud.application.usecase;

import com.medisalud.application.dto.TimeSlotDto;
import com.medisalud.application.mapper.DomainMapper;
import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.domain.model.TimeSlot;
import com.medisalud.domain.service.AvailabilityCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAvailableSlotsUseCaseTest {

    @Mock
    private AppointmentRepositoryPort appointments;

    @Mock
    private AvailabilityCalculator calculator;

    @Mock
    private DomainMapper mapper;

    @InjectMocks
    private FindAvailableSlotsUseCase useCase;

    @Test
    void shouldFindAvailableSlots() {
        UUID doctorId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        TimeSlot slot = new TimeSlot(date.atTime(8, 0), date.atTime(8, 30));
        TimeSlotDto dto = new TimeSlotDto(date.atTime(8, 0), date.atTime(8, 30));

        when(appointments.findBookedByDoctorAndRange(any(), any(), any())).thenReturn(Collections.emptyList());
        when(calculator.calculate(date, Collections.emptyList())).thenReturn(List.of(slot));
        when(mapper.toTimeSlotDto(slot)).thenReturn(dto);

        List<TimeSlotDto> result = useCase.execute(doctorId, date);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }
}
