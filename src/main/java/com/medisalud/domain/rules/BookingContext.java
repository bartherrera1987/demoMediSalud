package com.medisalud.domain.rules;

import com.medisalud.domain.model.Patient;
import com.medisalud.domain.model.TimeSlot;

import java.util.UUID;

public record BookingContext(UUID doctorId, Patient patient, TimeSlot slot) {
}