package hhplus.concert.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record UserResponse(
        Long id,
        String name,
        Long balance,
        List<Booking> bookings,
        List<Payment> payments
) {}
