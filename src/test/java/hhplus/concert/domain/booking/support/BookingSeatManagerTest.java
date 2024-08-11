package hhplus.concert.domain.booking.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.models.BookingStatus;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.concert.support.SeatValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static hhplus.concert.api.exception.code.BookingErrorCode.ALREADY_BOOKED;
import static hhplus.concert.api.exception.code.BookingErrorCode.PENDING_BOOKING;
import static hhplus.concert.domain.booking.models.BookingRule.BOOKING_EXPIRY_MINUTES;
import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static hhplus.concert.domain.booking.models.BookingStatus.INCOMPLETE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookingSeatManagerTest {

    @DisplayName("예약과 좌석의 상태를 검증하여 모두 검증되면 예외가 발생하지 않는다.")
    @Test
    void validateBookable() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong();

        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 11, 12, 00, 30);
        Booking booking1 = createBooking(bookingDateTime1, INCOMPLETE);
        Seat seat1 = createSeat(SeatBookingStatus.AVAILABLE);
        Seat seat2 = createSeat(SeatBookingStatus.AVAILABLE);

        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 11, 12, 03, 30);
        Booking booking2 = createBooking(bookingDateTime2, INCOMPLETE);
        Seat seat3 = createSeat(SeatBookingStatus.AVAILABLE);

        BookingSeatManager bookingSeatManager = new BookingSeatManager(new BookingValidator(), new SeatValidator());
        List<BookingSeat> bookingSeats1 = bookingSeatManager.createBookingSeats(List.of(seat1, seat2), booking1);
        List<BookingSeat> bookingSeats2 = bookingSeatManager.createBookingSeats(List.of(seat2, seat3), booking2);
        List<BookingSeat> bookingSeats = mergeBookingSeats(bookingSeats1, bookingSeats2);

        // when &then
        LocalDateTime validateTime = bookingDateTime2.plusMinutes(expiryMinutes);
        bookingSeatManager.validateBookable(bookingSeats, validateTime);
    }

    @DisplayName("예약들의 예약 시간이 모두 만료가 되었고 좌석의 예약상태가 유효 하더라도, 예약의 예약상태가 한 건이라도 완료상태이면 예외가 발생한다.")
    @Test
    void validateBookableWithAnyCompleteBooking() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong();

        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 11, 12, 00, 30);
        Booking booking1 = createBooking(bookingDateTime1, COMPLETE);
        Seat seat1 = createSeat(SeatBookingStatus.AVAILABLE);
        Seat seat2 = createSeat(SeatBookingStatus.AVAILABLE);

        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 11, 12, 03, 30);
        Booking booking2 = createBooking(bookingDateTime2, INCOMPLETE);
        Seat seat3 = createSeat(SeatBookingStatus.AVAILABLE);

        BookingSeatManager bookingSeatManager = new BookingSeatManager(new BookingValidator(), new SeatValidator());
        List<BookingSeat> bookingSeats1 = bookingSeatManager.createBookingSeats(List.of(seat1, seat2), booking1);
        List<BookingSeat> bookingSeats2 = bookingSeatManager.createBookingSeats(List.of(seat2, seat3), booking2);
        List<BookingSeat> bookingSeats = mergeBookingSeats(bookingSeats1, bookingSeats2);

        // when &then
        LocalDateTime validateTime = bookingDateTime2.plusMinutes(expiryMinutes);
        assertThatThrownBy(() -> bookingSeatManager.validateBookable(bookingSeats, validateTime))
                .isInstanceOf(RestApiException.class)
                .hasMessage(ALREADY_BOOKED.getMessage());
    }

    @DisplayName("예약들의 예약상태가 완료되지 않았고 좌석의 예약상태가 유효 하더라도, 예약의 예약 시간이 만료되지 않은 예약이 한 건이라도 있으면 예외가 발생한다.")
    @Test
    void validateBookableWithAnyNotExpiredBooking() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong();

        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 11, 12, 00, 30);
        Booking booking1 = createBooking(bookingDateTime1, INCOMPLETE);
        Seat seat1 = createSeat(SeatBookingStatus.AVAILABLE);
        Seat seat2 = createSeat(SeatBookingStatus.AVAILABLE);

        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 11, 12, 03, 30);
        Booking booking2 = createBooking(bookingDateTime2, INCOMPLETE);
        Seat seat3 = createSeat(SeatBookingStatus.AVAILABLE);

        BookingSeatManager bookingSeatManager = new BookingSeatManager(new BookingValidator(), new SeatValidator());
        List<BookingSeat> bookingSeats1 = bookingSeatManager.createBookingSeats(List.of(seat1, seat2), booking1);
        List<BookingSeat> bookingSeats2 = bookingSeatManager.createBookingSeats(List.of(seat2, seat3), booking2);
        List<BookingSeat> bookingSeats = mergeBookingSeats(bookingSeats1, bookingSeats2);

        // when &then
        LocalDateTime validateTime = bookingDateTime2.plusMinutes(expiryMinutes - 1);
        assertThatThrownBy(() -> bookingSeatManager.validateBookable(bookingSeats, validateTime))
                .isInstanceOf(RestApiException.class)
                .hasMessage(PENDING_BOOKING.getMessage());
    }

    @DisplayName("예약들의 예약 시간이 모두 만료가 되었고 예약의 예약상태가 유효 하더라도, 좌석의 예약상태가 한 건이라도 예약상태이면 예외가 발생한다.")
    @Test
    void validateBookableWithAnyBookedSeat() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong();

        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 11, 12, 00, 30);
        Booking booking1 = createBooking(bookingDateTime1, INCOMPLETE);
        Seat seat1 = createSeat(SeatBookingStatus.BOOKED);
        Seat seat2 = createSeat(SeatBookingStatus.AVAILABLE);

        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 11, 12, 03, 30);
        Booking booking2 = createBooking(bookingDateTime2, INCOMPLETE);
        Seat seat3 = createSeat(SeatBookingStatus.AVAILABLE);

        BookingSeatManager bookingSeatManager = new BookingSeatManager(new BookingValidator(), new SeatValidator());
        List<BookingSeat> bookingSeats1 = bookingSeatManager.createBookingSeats(List.of(seat1, seat2), booking1);
        List<BookingSeat> bookingSeats2 = bookingSeatManager.createBookingSeats(List.of(seat2, seat3), booking2);
        List<BookingSeat> bookingSeats = mergeBookingSeats(bookingSeats1, bookingSeats2);

        // when &then
        LocalDateTime validateTime = bookingDateTime2.plusMinutes(expiryMinutes);
        assertThatThrownBy(() -> bookingSeatManager.validateBookable(bookingSeats, validateTime))
                .isInstanceOf(RestApiException.class)
                .hasMessage(ALREADY_BOOKED.getMessage());
    }

    private static List<BookingSeat> mergeBookingSeats(List<BookingSeat>... bookingSeats) {
        return Stream.of(
                        bookingSeats
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static Seat createSeat(SeatBookingStatus seatBookingStatus) {
        return Seat.builder()
                .seatBookingStatus(seatBookingStatus)
                .build();
    }

    private static Booking createBooking(LocalDateTime bookingDateTime, BookingStatus bookingStatus) {
        return Booking.builder()
                .bookingDateTime(bookingDateTime)
                .bookingStatus(bookingStatus)
                .build();
    }

}