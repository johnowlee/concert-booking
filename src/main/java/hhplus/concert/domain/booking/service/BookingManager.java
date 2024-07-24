package hhplus.concert.domain.booking.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BookingErrorCode;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BookingManager {

    public void validateBookable(List<Booking> bookings) {
        checkAnyPendingBooking(bookings);
        checkAnyBookedSeat(bookings);
    }

    private void checkAnyPendingBooking(List<Booking> bookings) {
        bookings.forEach(Booking::validatePending);
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

    public List<BookingSeat> createBookingSeats(List<Seat> seats, Booking booking) {
        return seats.stream()
                .map(s -> BookingSeat.buildBookingSeat(booking, s))
                .collect(Collectors.toList());
    }
}
