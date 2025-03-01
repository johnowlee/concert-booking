package hhplus.concert.domain.booking.service;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.representer.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hhplus.concert.domain.history.payment.models.PaymentTimeLimitPolicy.ALLOWED_MINUTES;
import static hhplus.concert.representer.exception.code.BookingErrorCode.ALREADY_BOOKED;
import static hhplus.concert.representer.exception.code.BookingErrorCode.PENDING_BOOKING;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingValidator {

    public void validateBookability(List<BookingSeat> bookingSeats, LocalDateTime bookingDateTime) {
        Set<Booking> bookings = extractBookings(bookingSeats);
        checkAnyAlreadyCompleteBooking(bookings);
        checkAnyPendingBooking(bookings, bookingDateTime);
        checkAnyBookedSeat(extractSeats(bookingSeats));
    }

    private Set<Booking> extractBookings(List<BookingSeat> bookingSeats) {
        return bookingSeats.stream()
                .map(BookingSeat::getBooking)
                .collect(Collectors.toUnmodifiableSet());
    }

    private void checkAnyAlreadyCompleteBooking(Set<Booking> bookings) {
        bookings.stream()
                .filter(Booking::isBooked)
                .findFirst()
                .ifPresent(booking -> {
                    throw new RestApiException(ALREADY_BOOKED);
                });
    }

    private void checkAnyPendingBooking(Set<Booking> bookings, LocalDateTime localDateTime) {
        bookings.forEach(booking -> validatePendingBooking(booking, localDateTime));
    }

    private void checkAnyBookedSeat(List<Seat> seats) {
        seats.stream()
                .filter(Seat::isBooked)
                .findAny()
                .ifPresent(seat -> {
                    log.error("BookingErrorCode.ALREADY_BOOKED occurred for seat: {}", seat);
                    throw new RestApiException(ALREADY_BOOKED);
                });
    }

    private List<Seat> extractSeats(List<BookingSeat> bookingSeats) {
        return bookingSeats.stream()
                .map(BookingSeat::getSeat)
                .collect(Collectors.toList());
    }

    private static void validatePendingBooking(Booking booking, LocalDateTime verificationTime) {
        long passedMinutes = booking.getPassedMinutesSinceBookingFrom(verificationTime);
        if (isPayableTimeNotOver(passedMinutes)) {
            throw new RestApiException(PENDING_BOOKING);
        }
    }

    private static boolean isPayableTimeNotOver(long passedMinutes) {
        return passedMinutes < ALLOWED_MINUTES.getMinutes();
    }
}
