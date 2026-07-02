package com.medisalud.domain.rules;

public interface BusinessRule {
    void validate(BookingContext context);
    default String getName() {
        return this.getClass().getSimpleName();
    }
}