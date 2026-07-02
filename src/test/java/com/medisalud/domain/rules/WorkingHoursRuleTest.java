package com.medisalud.domain.rules;

import com.medisalud.domain.exception.WorkingHoursException;
import com.medisalud.domain.model.TimeSlot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WorkingHoursRuleTest {
    private final WorkingHoursRule rule = new WorkingHoursRule();

    @Test
    @DisplayName("Debería fallar si es domingo")
    void rejectsSunday() {
        assertThrows(WorkingHoursException.class, () -> validate("2026-07-05T09:00:00"));
    }

    @Test
    @DisplayName("Debería aceptar sábado en la mañana")
    void acceptsSaturdayMorning() {
        assertDoesNotThrow(() -> validate("2026-07-04T08:00:00"));
        assertDoesNotThrow(() -> validate("2026-07-04T12:30:00"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2026-07-04T07:30:00", "2026-07-04T13:00:00", "2026-07-04T15:00:00"})
    @DisplayName("Debería fallar si es sábado fuera de rango (8-13)")
    void rejectsSaturdayOutOfRange(String time) {
        assertThrows(WorkingHoursException.class, () -> validate(time));
    }

    @Test
    @DisplayName("Debería aceptar día de semana en rango")
    void acceptsWeekdayInRange() {
        assertDoesNotThrow(() -> validate("2026-07-02T08:00:00"));
        assertDoesNotThrow(() -> validate("2026-07-02T17:30:00"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2026-07-02T07:30:00", "2026-07-02T18:00:00", "2026-07-02T20:00:00"})
    @DisplayName("Debería fallar si es día de semana fuera de rango (8-18)")
    void rejectsWeekdayOutOfRange(String time) {
        assertThrows(WorkingHoursException.class, () -> validate(time));
    }

    private void validate(String start) {
        rule.validate(new BookingContext(null, null, TimeSlot.of(LocalDateTime.parse(start))));
    }
}