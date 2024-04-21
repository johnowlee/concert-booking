package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.common.ResponseResult;
import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.api.concert.dto.response.concertBooking.BookingResultResponse;
import hhplus.concert.domain.booking.components.BookingWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.models.BookingStatus;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.components.ConcertReader;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookConcertUseCase {

    private final UserReader userReader;
    private final ConcertReader concertReader;
    private final ConcertOptionReader concertOptionReader;
    private final BookingWriter bookingWriter;
    private final SeatReader seatReader;

    @Transactional
    public BookingResultResponse excute(String queueTokeinId, ConcertBookingRequest request) {
        // 토큰아이디로 유저 조회
        User user = userReader.getUserByUserId(2L);
        // 콘서트 옵션 id로 콘서트 옵션 조회
        ConcertOption concertOption = concertOptionReader.getConcertOptionById(request.concertOptionId());


        List<Long> seatIds = parseSeatIds(request.seatId());


        // 1. 예약테이블 일단 저장
        Booking savedBooking = bookingWriter.bookConcert(buildBooking(concertOption, user));
        // 2. 예약좌석매핑 테이블 저장
        bookingWriter.saveAllBookingSeat(createBookingSeats(seatIds, savedBooking));
        // 3. 좌석정보 조회
        List<Seat> seats = seatReader.getSeatsByIds(seatIds);

        return BookingResultResponse.of(ResponseResult.SUCCESS, user, savedBooking, concertOption, seats);
    }



    private static Booking buildBooking(ConcertOption concertOption, User user) {
        Booking booking = Booking.builder()
                                .bookingStatus(BookingStatus.INCOMPLETE)
                                .bookingDateTime(LocalDateTime.now())
                                .concertTitle(concertOption.getConcert().getTitle())
                                .user(user)
                                .build();
        return booking;
    }

    // booking_seat 생성
    private static List<BookingSeat> createBookingSeats(List<Long> seatIds, Booking savedBooking) {
        List<BookingSeat> bookingSeats = new ArrayList<>();
        for (Long seatId : seatIds) {
            Seat seat = Seat.builder()
                    .id(seatId)
                    .build();

            BookingSeat bookingSeat = BookingSeat.builder()
                    .booking(savedBooking)
                    .seat(seat)
                    .build();
            bookingSeats.add(bookingSeat);
        }
        return bookingSeats;
    }

    private static List<Long> parseSeatIds(String seatIds) {
        return Arrays.stream(seatIds.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
    }
}
