package hhplus.concert.domain.booking.support;

import hhplus.concert.domain.booking.components.BookingSeatReader;
import hhplus.concert.domain.booking.components.BookingSeatWriter;
import hhplus.concert.domain.booking.components.BookingWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.support.SeatManager;
import hhplus.concert.domain.support.ClockManager;
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
    private final BookingSeatWriter bookingSeatWriter;
    private final SeatManager seatManager;
    private final BookingSeatManager bookingSeatManager;
    private final BookingSeatReader bookingSeatReader;
    private final ClockManager clockManager;

    public Booking book(User user, List<Seat> seats) {
        String concertTitle = seats.get(0).getConcertOption().getConcert().getTitle();
        Booking booking = Booking.buildBooking(concertTitle, user);

        // 예약테이블 저장
        Booking savedBooking = bookingWriter.bookConcert(booking);

        // 예약좌석매핑 테이블 저장
        bookingSeatWriter.saveBookingSeats(bookingSeatManager.createBookingSeats(seats, savedBooking));

        // 좌석들 예약 진행 중으로 상태 변경
        seatManager.markAllSeatsAsProcessing(seats);
        return savedBooking;
    }

    public void validateBookability(List<Long> seatIds) {
        List<BookingSeat> bookingSeats = bookingSeatReader.getBookingSeatsBySeatIds(seatIds);
        bookingSeatManager.validateBookable(bookingSeats, clockManager.getNowDateTime());
    }
}
