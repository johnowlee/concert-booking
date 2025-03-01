package hhplus.concert.application.user;

import hhplus.concert.application.user.dto.ConcertBookingDto;
import hhplus.concert.domain.booking.components.BookingSeatReader;
import hhplus.concert.domain.booking.components.BookingSeatWriter;
import hhplus.concert.domain.booking.components.BookingWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.service.BookingValidator;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookConcertUseCase {

    private final SeatReader seatReader;
    private final UserReader userReader;
    private final BookingWriter bookingWriter;
    private final BookingSeatWriter bookingSeatWriter;
    private final BookingValidator bookingValidator;
    private final BookingSeatReader bookingSeatReader;
    private final ClockManager clockManager;

    @Transactional
    public Booking execute(Long userId, ConcertBookingDto dto) {
        List<BookingSeat> bookingSeats = bookingSeatReader.getBookingSeatsBySeatIds(dto.seatIds());
        LocalDateTime bookingDateTime = clockManager.getNowDateTime();

        bookingValidator.validateBookability(bookingSeats, bookingDateTime);

        List<Seat> seats = seatReader.getSeatsByIds(dto.seatIds());
        User user = userReader.getUserById(userId);

        Booking booking = saveBooking(seats, bookingDateTime, user);
        saveBookingSeats(seats, booking);
        return booking;
    }

    private void saveBookingSeats(List<Seat> seats, Booking booking) {
        List<BookingSeat> bookingSeats = createBookingSeats(seats, booking);
        bookingSeatWriter.saveBookingSeats(bookingSeats);
        seats.forEach(Seat::markAsProcessing);
    }

    private Booking saveBooking(List<Seat> seats, LocalDateTime bookingDateTime, User user) {
        Booking booking = Booking.initializeBooking(extractConcertTitle(seats), bookingDateTime, user);
        bookingWriter.saveBooking(booking);
        return booking;
    }

    private static String extractConcertTitle(List<Seat> seats) {
        return seats.get(0).getConcertOption().getConcert().getTitle();
    }

    private static List<BookingSeat> createBookingSeats(List<Seat> seats, Booking booking) {
        return seats.stream()
                .map(s -> BookingSeat.createBookingSeat(booking, s))
                .collect(Collectors.toList());
    }
}
