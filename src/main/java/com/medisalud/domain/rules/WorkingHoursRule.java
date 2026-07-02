package com.medisalud.domain.rules;

import com.medisalud.domain.exception.WorkingHoursException;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WorkingHoursRule implements BusinessRule {
    @Override
    public void validate(BookingContext context) {
        DayOfWeek day = context.slot().start().getDayOfWeek();
        LocalTime start = context.slot().startTime();
        LocalTime end = context.slot().end().toLocalTime();
        if (day == DayOfWeek.SUNDAY) {
            throw new WorkingHoursException("No hay atención los domingos");
        }
        if (day == DayOfWeek.SATURDAY) {
            requireBetween(start, end, LocalTime.of(8, 0), LocalTime.of(13, 0));
            return;
        }
        requireBetween(start, end, LocalTime.of(8, 0), LocalTime.of(18, 0));
    }

    private void requireBetween(LocalTime start, LocalTime end, LocalTime open, LocalTime close) {
        if (start.isBefore(open) || end.isAfter(close)) {
            throw new WorkingHoursException("La cita está fuera del horario de atención");
        }
    }
}