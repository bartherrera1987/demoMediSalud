package com.medisalud.infrastructure.config;

import com.medisalud.application.ports.AppointmentRepositoryPort;
import com.medisalud.application.ports.PenaltyRepositoryPort;
import com.medisalud.domain.rules.*;
import com.medisalud.domain.service.BookingValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainConfig {

    @Bean
    public BookingValidator bookingValidator(AppointmentRepositoryPort appointments, PenaltyRepositoryPort penalties) {
        return new BookingValidator(List.of(
                new WorkingHoursRule(),
                new PatientAgeRule(),
                new DoctorAvailabilityRule(appointments),
                new PatientConflictRule(appointments),
                new PatientPenaltyRule(penalties)
        ));
    }
}
