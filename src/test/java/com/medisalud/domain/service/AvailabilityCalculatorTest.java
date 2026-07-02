package com.medisalud.domain.service;

import com.medisalud.domain.model.*;
import com.medisalud.domain.rules.WorkingHoursRule;
import com.medisalud.domain.service.AvailabilityCalculator;
import com.medisalud.domain.service.BookingValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AvailabilityCalculatorTest {
    private final BookingValidator validator = new BookingValidator(List.of(new WorkingHoursRule()));
    private final AvailabilityCalculator calculator = new AvailabilityCalculator(validator);

    @Test
    @DisplayName("Debería excluir franjas horarias ocupadas")
    void excludesBusySlot() {
        LocalDate date = LocalDate.parse("2026-07-02"); // Jueves
        LocalDateTime busyStart = LocalDateTime.parse("2026-07-02T09:00:00");
        Appointment busy = new Appointment(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 
            TimeSlot.of(busyStart), AppointmentStatus.BOOKED, busyStart.minusDays(1), null);
        
        List<TimeSlot> slots = calculator.calculate(date, List.of(busy));
        
        assertFalse(slots.stream().anyMatch(s -> s.start().equals(busyStart)), "La franja ocupada no debería estar disponible");
        assertTrue(slots.stream().anyMatch(s -> s.start().equals(LocalDateTime.parse("2026-07-02T08:00:00"))), "Otras franjas deberían estar disponibles");
    }

    @Test
    @DisplayName("No debería retornar franjas en domingo")
    void returnsNoSlotsOnSunday() {
        assertTrue(calculator.calculate(LocalDate.parse("2026-07-05"), List.of()).isEmpty());
    }

    @Test
    @DisplayName("Debería retornar franjas limitadas el sábado")
    void returnsLimitedSlotsOnSaturday() {
        LocalDate saturday = LocalDate.parse("2026-07-04");
        List<TimeSlot> slots = calculator.calculate(saturday, List.of());
        
        assertFalse(slots.isEmpty(), "Debería haber franjas el sábado");
        assertTrue(slots.stream().allMatch(s -> s.startTime().isBefore(java.time.LocalTime.of(13, 0))), "Todas las franjas del sábado deben ser antes de las 13:00");
        assertTrue(slots.stream().anyMatch(s -> s.startTime().equals(java.time.LocalTime.of(12, 30))), "Debería incluir la franja de las 12:30");
    }

    @Test
    @DisplayName("Debería retornar todas las franjas en un día hábil")
    void returnsAllSlotsOnWeekday() {
        LocalDate weekday = LocalDate.parse("2026-07-02");
        List<TimeSlot> slots = calculator.calculate(weekday, List.of());
        
        // 8:00 a 18:00 cada 30 min = 20 franjas
        assertEquals(20, slots.size());
        assertEquals(java.time.LocalTime.of(8, 0), slots.get(0).startTime());
        assertEquals(java.time.LocalTime.of(17, 30), slots.get(slots.size() - 1).startTime());
    }
}