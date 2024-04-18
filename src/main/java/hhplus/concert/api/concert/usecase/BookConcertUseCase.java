package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.api.concert.dto.response.BookingResultResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.components.BookingWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.components.ConcertReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookConcertUseCase {
    private final EntityManager entityManager;
    private final UserReader userReader;
    private final ConcertReader concertReader;
    private final BookingWriter bookingWriter;
    private final BookingReader bookingReader;

    @Transactional
    public BookingResultResponse excute(String queueTokeinId, ConcertBookingRequest request) {
//        // 토큰아이디로 유저 조회
//        User user = userReader.getUserByUserId(1L);
//        // 콘서트 옵션 id로 콘서트 옵션 조회
//        ConcertOption concertOption = concertReader.getConcertOptionById(request.concertOptionId());
//
//
//        // 1. 예약테이블 일단 먼저 저장
//        Booking savedBooking = bookingWriter.bookConcert(buildBooking(concertOption, user));
//        entityManager.flush();
//        // 2. 예약좌석매핑 테이블 저장
//        List<BookingSeat> savedBookingSeats = bookingWriter.saveAllBookingSeat(createBookingSeats(request.seatId(), savedBooking));
        return null;
    }

    private static Booking buildBooking(ConcertOption concertOption, User user) {
//        Booking booking = Booking.builder()
//                                .bookingStatus(BookingStatus.INCOMPLETE)
//                                .bookingDateTime(LocalDateTime.now())
//                                .concertTitle(concertOption.getConcert().getTitle())
//                                .user(user)
//                                .build();
//        return booking;
        return null;
    }

    // booking_seat 생성
    private static List<BookingSeat> createBookingSeats(String seatIds, Booking savedBooking) {
//        List<BookingSeat> bookingSeats = new ArrayList<>();
//        for (String seatId : seatIds.split(",")) {
//            Seat seat = Seat.builder()
//                    .id(Long.parseLong(seatId))
//                    .build();
//
//            BookingSeat bookingSeat = BookingSeat.builder()
//                    .booking(savedBooking)
//                    .seat(seat)
//                    .build();
//            bookingSeats.add(bookingSeat);
//        }
//        return bookingSeats;
        return null;
    }
}
