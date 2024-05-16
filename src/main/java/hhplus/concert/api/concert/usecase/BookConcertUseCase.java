package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.api.concert.dto.response.concertBooking.BookingResultResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.components.BookingWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.components.SeatValidator;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.queue.service.TokenValidator;
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
    private final BookingWriter bookingWriter;
    private final SeatReader seatReader;
    private final BookingReader bookingReader;
    private final SeatValidator seatValidator;
    private final UserReader userReader;
    private final TokenValidator tokenValidator;

    @Transactional
    public BookingResultResponse execute(String token, Long optionId, ConcertBookingRequest request) {
        // 1. 토큰 검증
        tokenValidator.validateToken(token);

        // 2. 유저 조회
        User user = userReader.getUserById(request.userId());

        // 3. 좌석정보 조회
        List<Seat> seats = seatReader.getSeatsByIds(request.parsedSeatIds());

        // 4. 예약상태, 좌석상태 검증
        List<Booking> bookingsBySeats = bookingReader.getBookingsBySeatIds(request.parsedSeatIds());
        seatValidator.validateSeatsBookable(bookingsBySeats);

        // 5. 콘서트 옵션 id로 콘서트 옵션 조회
        ConcertOption concertOption = concertOptionReader.getConcertOptionById(optionId);


        // 6. 예약테이블 저장
        Booking savedBooking = bookingWriter.bookConcert(Booking.buildBooking(concertOption, user));

        // 7. 예약좌석매핑 테이블 저장
        bookingWriter.saveAllBookingSeat(BookingSeat.createBookingSeats(seats, savedBooking));

        // 8. 좌석 예약상태 update
        updateSeatBookingStatus(seats, SeatBookingStatus.PROCESSING);

        return BookingResultResponse.succeed(user, savedBooking, concertOption, seats);
    }

    private static void updateSeatBookingStatus(List<Seat> seats, SeatBookingStatus status) {
        seats.stream().forEach(s -> s.changeBookingStatus(status));
    }
}
