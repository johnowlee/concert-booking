package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.api.concert.dto.response.concertBooking.BookingResultResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.service.BookingService;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.ConcertOption;
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
    private final BookingReader bookingReader;
    private final UserReader userReader;
    private final BookingService bookingService;

    @Transactional
    public BookingResultResponse execute(Long optionId, ConcertBookingRequest request) {

        // 1. 예약상태, 좌석상태 검증
        List<Booking> bookingsBySeats = bookingReader.getBookingsBySeatIds(request.parsedSeatIds());
        bookingService.validateBookable(bookingsBySeats);

        // 2. 콘서트 옵션 id로 콘서트 옵션 조회
        ConcertOption concertOption = concertOptionReader.getConcertOptionById(optionId);

        // 3. 유저 조회
        User user = userReader.getUserById(request.userId());

        // 4. 좌석정보 조회
        List<Seat> seats = seatReader.getSeatsByIds(request.parsedSeatIds());

        // 5. 콘서트 예약
        Booking booking = bookingService.book(concertOption, user, seats);

        return BookingResultResponse.succeed(user, booking, concertOption, seats);
    }
}
