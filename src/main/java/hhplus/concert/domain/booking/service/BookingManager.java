package hhplus.concert.domain.booking.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BookingErrorCode;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BookingManager {

    public void validateBookable(List<Booking> bookingsBySeats) {
        checkAnyProcessingBooking(bookingsBySeats);
        checkAnyBookedSeat(bookingsBySeats);
    }

    private void checkAnyProcessingBooking(List<Booking> bookingsBySeats) {
        boolean hasAnyBooking = bookingsBySeats.stream().anyMatch(b -> !b.isBookingDateTimeExpired());
        if (hasAnyBooking) {
            log.error("BookingErrorCode.PROCESSING_BOOKING 발생");
            throw new RestApiException(BookingErrorCode.PROCESSING_BOOKING);
        }
    }

    private void checkAnyBookedSeat(List<Booking> bookingsBySeats) {
        bookingsBySeats.stream()
                .flatMap(booking -> booking.getBookingSeats().stream())
                .filter(bookingSeat -> bookingSeat.getSeat().isBooked())
                .findAny()
                .ifPresent(bs -> {
                    log.error("BookingErrorCode.ALREADY_BOOKED 발생");
                    throw new RestApiException(BookingErrorCode.ALREADY_BOOKED);
                });
    }

    public void reserveAllSeats(Booking booking) {
        booking.getBookingSeats().stream()
                .map(BookingSeat::getSeat)
                .forEach(seat -> seat.markAsBooked());
    }
}
