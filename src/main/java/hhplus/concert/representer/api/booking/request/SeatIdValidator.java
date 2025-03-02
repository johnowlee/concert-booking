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
                .allMatch(seatId -> isPositiveNumeric(seatId));
    }

    private boolean isPositiveNumeric(String str) {
        try {
            long parsedSeatId = Long.parseLong(str.trim());
            if (parsedSeatId > 0) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
