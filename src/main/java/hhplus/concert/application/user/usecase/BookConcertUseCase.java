package hhplus.concert.application.user.usecase;

import hhplus.concert.application.user.data.command.ConcertBookingCommand;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.model.BookingSeat;
import hhplus.concert.core.booking.domain.service.BookingCommandService;
import hhplus.concert.core.booking.domain.service.BookingQueryService;
import hhplus.concert.core.booking.domain.service.BookingValidator;
import hhplus.concert.core.concert.domain.model.Seat;
import hhplus.concert.core.concert.domain.service.ConcertQueryService;
import hhplus.concert.application.support.ClockManager;
import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.domain.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookConcertUseCase {

    private final ConcertQueryService concertQueryService;
    private final UserQueryService userQueryService;
    private final BookingCommandService bookingCommandService;
    private final BookingQueryService bookingQueryService;
    private final BookingValidator bookingValidator;
    private final ClockManager clockManager;

    public Booking execute(Long userId, ConcertBookingCommand command) {
        List<BookingSeat> bookingSeats = bookingQueryService.findBookingSeatsBySeatIds(command.seatIds());
        LocalDateTime bookingDateTime = clockManager.getNowDateTime();

        bookingValidator.validateBookability(bookingSeats, bookingDateTime);

        List<Seat> seats = concertQueryService.findSeatsByIdsWithLock(command.seatIds());
        User user = userQueryService.getUserById(userId);

        Booking booking = saveBooking(seats, bookingDateTime, user);
        saveBookingSeats(seats, booking);
        return booking;
    }

    private void saveBookingSeats(List<Seat> seats, Booking booking) {
        List<BookingSeat> bookingSeats = createBookingSeats(seats, booking);
        bookingCommandService.saveBookingSeats(bookingSeats);
        seats.forEach(Seat::markAsProcessing);
    }

    private Booking saveBooking(List<Seat> seats, LocalDateTime bookingDateTime, User user) {
        Booking booking = Booking.initializeBooking(extractConcertTitle(seats), bookingDateTime, user);
        bookingCommandService.saveBooking(booking);
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
