package hhplus.concert.domain.booking.support;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.support.SeatValidator;
import hhplus.concert.domain.support.ClockManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookingSeatManager {

    private final BookingValidator bookingValidator;
    private final SeatValidator seatValidator;

    public void validateBookable(List<BookingSeat> bookingSeats, ClockManager clockManager) {
        List<Booking> bookings = extractBookings(bookingSeats);
        bookingValidator.checkAnyAlreadyCompleteBooking(bookings);
        bookingValidator.checkAnyPendingBooking(bookings, clockManager.getNowDateTime());
        seatValidator.checkAnyBookedSeat(extractSeats(bookingSeats));
    }

    public List<BookingSeat> createBookingSeats(List<Seat> seats, Booking booking) {
        return seats.stream()
                .map(s -> BookingSeat.createBookingSeat(booking, s))
                .collect(Collectors.toList());
    }

    private List<Booking> extractBookings(List<BookingSeat> bookingSeats) {
        return bookingSeats.stream()
                .map(BookingSeat::getBooking)
                .collect(Collectors.toList());
    }

    private List<Seat> extractSeats(List<BookingSeat> bookingSeats) {
        return bookingSeats.stream()
                .map(BookingSeat::getSeat)
                .collect(Collectors.toList());
    }
}
