package com.medisalud.domain.service;

import com.medisalud.domain.model.Appointment;
import com.medisalud.domain.model.TimeSlot;
import com.medisalud.domain.rules.BookingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AvailabilityCalculator {
    private final BookingValidator validator;

    public AvailabilityCalculator(BookingValidator validator) {
        this.validator = validator;
    }

    public List<TimeSlot> calculate(LocalDate date, List<Appointment> busyAppointments) {
        return java.util.stream.Stream.iterate(LocalDateTime.of(date, LocalTime.of(8, 0)), t -> t.plusMinutes(30))
                .limit(20)
                .map(TimeSlot::of)
                .filter(slot -> isAvailable(slot, busyAppointments))
                .toList();
    }

    private boolean isAvailable(TimeSlot slot, List<Appointment> busyAppointments) {
        try {
            validator.validate(new BookingContext(null, null, slot));
            return busyAppointments.stream().noneMatch(a -> a.slot().start().equals(slot.start()));
        } catch (RuntimeException exception) {
            return false;
        }
    }
}