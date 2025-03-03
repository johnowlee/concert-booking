package hhplus.concert.representer.api.booking.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class SeatIdValidator implements ConstraintValidator<ValidSeatIds, String> {
    @Override
    public void initialize(ValidSeatIds constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String seatIds, ConstraintValidatorContext context) {
        return Arrays.stream(seatIds.split(","))
                .allMatch(this::isPositiveNumeric);
    }

    private boolean isPositiveNumeric(String str) {
        try {
            long parsedSeatId = Long.parseLong(str.trim());
            return parsedSeatId > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
