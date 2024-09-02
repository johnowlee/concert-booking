package hhplus.concert.domain.booking.service;

import hhplus.concert.domain.booking.components.BookingSeatReader;
import hhplus.concert.domain.booking.components.BookingSeatWriter;
import hhplus.concert.domain.booking.components.BookingWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.support.BookingSeatManager;
import hhplus.concert.domain.booking.support.BookingValidator;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.support.SeatManager;
import hhplus.concert.domain.concert.support.SeatValidator;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingService {

    private final BookingWriter bookingWriter;
    private final BookingSeatWriter bookingSeatWriter;
    private final SeatManager seatManager;
    private final BookingSeatManager bookingSeatManager;
    private final BookingSeatReader bookingSeatReader;
    private final ClockManager clockManager;

    private final BookingValidator bookingValidator;
    private final SeatValidator seatValidator;

    public Booking book(User user, List<Seat> seats) {
        Booking booking = initializeBooking(user, seats);

        bookingWriter.bookConcert(booking);

        saveBookingSeats(seats, booking);

        seatManager.markAllSeatsAsProcessing(seats);
        return booking;
    }

    public void validateBookability(List<Long> seatIds) {
        List<BookingSeat> bookingSeats = bookingSeatReader.getBookingSeatsBySeatIds(seatIds);
        Set<Booking> bookings = extractBookings(bookingSeats);
        bookingValidator.checkAnyAlreadyCompleteBooking(bookings);
        bookingValidator.checkAnyPendingBooking(bookings, clockManager.getNowDateTime());
        seatValidator.checkAnyBookedSeat(extractSeats(bookingSeats));
    }

    private Set<Booking> extractBookings(List<BookingSeat> bookingSeats) {
        return bookingSeats.stream()
                .map(BookingSeat::getBooking)
                .collect(Collectors.toUnmodifiableSet());
    }

    private List<Seat> extractSeats(List<BookingSeat> bookingSeats) {
        return bookingSeats.stream()
                .map(BookingSeat::getSeat)
                .collect(Collectors.toList());
    }

    private Booking initializeBooking(User user, List<Seat> seats) {
        String concertTitle = seatManager.getConcertTitleFrom(seats);
        return Booking.initializeBooking(concertTitle, clockManager, user);
    }

    private void saveBookingSeats(List<Seat> seats, Booking booking) {
        List<BookingSeat> bookingSeats = bookingSeatManager.createBookingSeats(seats, booking);
        bookingSeatWriter.saveBookingSeats(bookingSeats);
    }
}
