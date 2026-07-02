package com.medisalud.domain.service;

import com.medisalud.domain.rules.BookingContext;
import com.medisalud.domain.rules.BusinessRule;

import java.util.List;

public class BookingValidator {
    private final List<BusinessRule> rules;

    public BookingValidator(List<BusinessRule> rules) {
        this.rules = rules;
    }

    public void validate(BookingContext context) {
        for (BusinessRule rule : rules) {
            rule.validate(context);
        }
    }
}
