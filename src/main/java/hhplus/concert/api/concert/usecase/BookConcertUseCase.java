package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.controller.request.ConcertBookingRequest;
import hhplus.concert.api.concert.usecase.response.BookConcertResponse;
import hhplus.concert.domain.booking.components.BookingSeatWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.support.BookingService;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookConcertUseCase {

    private final SeatReader seatReader;
    private final UserReader userReader;
    private final BookingService bookingService;

    @Transactional
    public BookConcertResponse execute(ConcertBookingRequest request) {

        validateBookability(request);

        Booking booking = bookConcert(request);

        return BookConcertResponse.from(booking);
    }

    private void validateBookability(ConcertBookingRequest request) {
        bookingService.validateBookability(request.seatIds());
    }

    private Booking bookConcert(ConcertBookingRequest request) {
        User user = userReader.getUserById(request.userId());

        List<Seat> seats = seatReader.getSeatsByIds(request.seatIds());

        return bookingService.book(user, seats);
    }
}
