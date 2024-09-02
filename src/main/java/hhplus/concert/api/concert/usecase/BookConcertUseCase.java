package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.concert.controller.request.ConcertBookingRequest;
import hhplus.concert.api.concert.usecase.response.BookConcertResponse;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.service.BookingService;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@UseCase
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
