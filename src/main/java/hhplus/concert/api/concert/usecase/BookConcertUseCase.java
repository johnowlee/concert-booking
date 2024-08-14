package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.controller.request.ConcertBookingRequest;
import hhplus.concert.api.concert.usecase.response.concertBooking.BookingResultResponse;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.support.BookingService;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookConcertUseCase {

    private final ConcertOptionReader concertOptionReader;
    private final SeatReader seatReader;
    private final UserReader userReader;
    private final BookingService bookingService;

    @Transactional
    public BookingResultResponse execute(Long optionId, ConcertBookingRequest request) {

        // 1. 예약상태, 좌석상태 검증
        bookingService.validateBookability(request.parsedSeatIds());

        // 2. 유저 조회
        User user = userReader.getUserById(request.userId());

        // 3. 좌석정보 조회
        List<Seat> seats = seatReader.getSeatsByIds(request.parsedSeatIds());

        // 4. 콘서트 예약
        Booking booking = bookingService.book(user, seats);

        return BookingResultResponse.succeed(user, booking, seats);
    }
}
