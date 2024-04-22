package hhplus.concert.domain.booking.components;

import hhplus.concert.domain.booking.models.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingValidator {

    public boolean isExpired(Booking booking) {
        return booking.isBookingDateTimeExpired();
    }

}
