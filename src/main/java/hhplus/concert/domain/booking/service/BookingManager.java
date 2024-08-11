package hhplus.concert.domain.booking.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BookingErrorCode;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BookingManager {

    public void validateBookable(List<Booking> bookings, LocalDateTime localDateTime) {
        checkAnyPendingBooking(bookings, localDateTime);
        checkAnyBookedSeat(bookings);
    }

    private void checkAnyPendingBooking(List<Booking> bookings, LocalDateTime localDateTime) {
        bookings.forEach(booking -> booking.validatePendingBooking(localDateTime));
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

    public List<BookingSeat> createBookingSeats(List<Seat> seats, Booking booking) {
        return seats.stream()
                .map(s -> BookingSeat.buildBookingSeat(booking, s))
                .collect(Collectors.toList());
    }
}
