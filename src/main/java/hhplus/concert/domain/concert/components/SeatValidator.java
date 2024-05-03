package hhplus.concert.domain.concert.components;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BookingErrorCode;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatValidator {

    public void validateSeatsBookable(List<Booking> bookingsBySeats) {
        checkAnyProcessingBooking(bookingsBySeats);
        checkAnyBookedSeat(bookingsBySeats);
    }

    private static void checkAnyProcessingBooking(List<Booking> bookingsBySeats) {
        boolean hasAnyBooking = bookingsBySeats.stream().anyMatch(b -> !b.isBookingDateTimeExpired());
        if (hasAnyBooking) {
            throw new RestApiException(BookingErrorCode.PROCESSING_BOOKING);
        }
    }

    private static void checkAnyBookedSeat(List<Booking> bookingsBySeats) {
        bookingsBySeats.forEach(booking -> {
            booking.getBookingSeats().stream()
                    .filter(bookingSeat -> bookingSeat.getSeat().getSeatBookingStatus() == SeatBookingStatus.BOOKED)
                    .findAny()
                    .ifPresent(bs -> {
                        throw new RestApiException(BookingErrorCode.ALREADY_BOOKED);
                    });
        });
    }

}
