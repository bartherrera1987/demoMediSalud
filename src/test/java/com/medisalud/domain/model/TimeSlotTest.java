package com.medisalud.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    @DisplayName("Debería crear TimeSlot válido")
    void validTimeSlot() {
        LocalDateTime start = LocalDateTime.now().withMinute(30).withSecond(0).withNano(0);
        TimeSlot slot = TimeSlot.of(start);
        assertEquals(start, slot.start());
        assertEquals(start.plusMinutes(30), slot.end());
    }

    @Test
    @DisplayName("Debería fallar si no dura 30 minutos")
    void invalidDuration() {
        LocalDateTime start = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(start, start.plusMinutes(31)));
    }

    @Test
    @DisplayName("Debería fallar si no inicia en minuto 0 o 30")
    void invalidStartMinute() {
        LocalDateTime start = LocalDateTime.now().withMinute(15).withSecond(0).withNano(0);
        assertThrows(IllegalArgumentException.class, () -> TimeSlot.of(start));
    }
    
    @Test
    @DisplayName("Debería fallar si start o end son null")
    void nullParams() {
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(null, LocalDateTime.now().withMinute(0)));
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(null, null));
    }
}
