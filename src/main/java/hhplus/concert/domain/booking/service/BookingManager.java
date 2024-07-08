package hhplus.concert.domain.booking.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BookingErrorCode;
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
public class BookingManager {
    private final BookingWriter bookingWriter;

    public Booking book(ConcertOption concertOption, User user, List<Seat> seats) {
        Booking booking = Booking.buildBooking(concertOption, user);

        // 예약테이블 저장
        Booking savedBooking = bookingWriter.bookConcert(booking);

        // 예약좌석매핑 테이블 저장
        bookingWriter.saveAllBookingSeat(BookingSeat.createBookingSeats(seats, savedBooking));

        // 좌석 예약상태 update
        updateSeatBookingStatus(seats, SeatBookingStatus.PROCESSING);
        return savedBooking;
    }

    private static void updateSeatBookingStatus(List<Seat> seats, SeatBookingStatus status) {
        seats.stream().forEach(s -> s.changeBookingStatus(status));
    }

    public void validateBookable(List<Booking> bookingsBySeats) {
        checkAnyProcessingBooking(bookingsBySeats);
        checkAnyBookedSeat(bookingsBySeats);
    }

    private static void checkAnyProcessingBooking(List<Booking> bookingsBySeats) {
        boolean hasAnyBooking = bookingsBySeats.stream().anyMatch(b -> !b.isBookingDateTimeExpired());
        if (hasAnyBooking) {
            log.error("BookingErrorCode.PROCESSING_BOOKING 발생");
            throw new RestApiException(BookingErrorCode.PROCESSING_BOOKING);
        }
    }

    private static void checkAnyBookedSeat(List<Booking> bookingsBySeats) {
        bookingsBySeats.forEach(booking -> {
            booking.getBookingSeats().stream()
                    .filter(bookingSeat -> bookingSeat.getSeat().getSeatBookingStatus() == SeatBookingStatus.BOOKED)
                    .findAny()
                    .ifPresent(bs -> {
                        log.error("BookingErrorCode.ALREADY_BOOKED 발생");
                        throw new RestApiException(BookingErrorCode.ALREADY_BOOKED);
                    });
        });
    }
}
