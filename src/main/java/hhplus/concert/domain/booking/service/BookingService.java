package hhplus.concert.domain.booking.service;

import hhplus.concert.domain.booking.components.BookingWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookingService {

    private final BookingWriter bookingWriter;
    private final BookingManager bookingManager;

    public Booking book(ConcertOption concertOption, User user, List<Seat> seats) {
        Booking booking = Booking.buildBooking(concertOption, user);

        // 예약테이블 저장
        Booking savedBooking = bookingWriter.bookConcert(booking);

        // 예약좌석매핑 테이블 저장
        bookingWriter.saveAllBookingSeat(BookingSeat.createBookingSeats(seats, savedBooking));

        // 좌석 예약상태 update
        bookingManager.changeSeatBookingStatus(seats, SeatBookingStatus.PROCESSING);
        return savedBooking;
    }
}
