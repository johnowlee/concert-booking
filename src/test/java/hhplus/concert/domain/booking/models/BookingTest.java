package hhplus.concert.domain.booking.models;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.concert.models.SeatGrade;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static hhplus.concert.api.exception.code.BookingErrorCode.*;
import static hhplus.concert.domain.booking.models.BookingRule.BOOKING_EXPIRY_MINUTES;
import static hhplus.concert.domain.booking.models.BookingStatus.*;
import static hhplus.concert.domain.concert.models.SeatBookingStatus.AVAILABLE;
import static hhplus.concert.domain.concert.models.SeatBookingStatus.PROCESSING;
import static org.assertj.core.api.Assertions.*;

class BookingTest {

    @DisplayName("예약 상태를 COMPLETE 상태로 변경한다.")
    @Test
    void markAsComplete() {
        // given
        Booking booking = Booking.builder().bookingStatus(INCOMPLETE).build();

        // when
        booking.markAsComplete();

        // then
        assertThat(booking.getBookingStatus()).isEqualTo(COMPLETE);
    }

    @DisplayName("booking 상태가 COMPLETE이면 예외가 발생한다.")
    @Test
    void validateAlreadyBooked() {
        // given
        Booking booking = Booking.builder()
                .bookingStatus(COMPLETE)
                .build();

        // when & then
        assertThatThrownBy(() -> booking.validateAlreadyBooked())
                .isInstanceOf(RestApiException.class)
                .hasMessage(ALREADY_BOOKED.getMessage());
    }

    @DisplayName("예약의 예약시간이 만료되어 유효하지 않은 상태이면 예외가 발생한다.")
    @Test
    void validateBookingDateTime() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong();
        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 11, 11, 00);
        Booking booking = Booking.builder()
                .bookingDateTime(bookingDateTime)
                .build();

        // when & then
        LocalDateTime validateTime = bookingDateTime.plusMinutes(expiryMinutes);
        assertThatThrownBy(() -> booking.validateBookingDateTime(validateTime))
                .isInstanceOf(RestApiException.class)
                .hasMessage(EXPIRED_BOOKING_TIME.getMessage());
    }


    @DisplayName("예약의 예약시간이 유효한 상태이면 예외가 발생한다.")
    @Test
    void validatePendingBooking() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong();
        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 11, 11, 00);
        Booking booking = Booking.builder()
                .bookingDateTime(bookingDateTime)
                .build();

        // when & then
        LocalDateTime validateTime = bookingDateTime.plusMinutes(expiryMinutes - 1);
        assertThatThrownBy(() -> booking.validatePendingBooking(validateTime))
                .isInstanceOf(RestApiException.class)
                .hasMessage(PENDING_BOOKING.getMessage());
    }

    @DisplayName("예약된 좌석들의 가격을 모두 더해 총 예약 금액을 가져온다.")
    @Test
    void getTotalPrice() {
        // given
        Seat seat1 = Seat.builder()
                .seatNo("A-1")
                .grade(SeatGrade.A)
                .build();
        Seat seat2 = Seat.builder()
                .seatNo("B-1")
                .grade(SeatGrade.B)
                .build();
        BookingSeat bookingSeat1 = BookingSeat.builder()
                .seat(seat1)
                .build();
        BookingSeat bookingSeat2 = BookingSeat.builder()
                .seat(seat2)
                .build();
        Booking booking = Booking.builder()
                .bookingSeats(List.of(bookingSeat1, bookingSeat2))
                .build();

        // when
        int result = booking.getTotalPrice();

        // then
        assertThat(result).isEqualTo(seat1.getPrice() + seat2.getPrice());
    }

    @DisplayName("예약자의 userId와 같지 않다면, 예외를 터트린다.")
    @Test
    void validatePayer() {
        // given
        User user = User.builder().id(1L).build();
        Booking booking = Booking.builder().user(user).build();

        // when & then
        assertThatThrownBy(() -> booking.validatePayer(2L))
                .isInstanceOf(RestApiException.class)
                .hasMessage(INVALID_PAYER.getMessage());
    }

    @DisplayName("예약자의 userId와 같다면, 아무일도 일어나지 않는다.")
    @Test
    void validatePayeWithSameUser() {
        // given
        User user = User.builder().id(1L).build();
        Booking booking = Booking.builder().user(user).build();

        // when & then
        booking.validatePayer(1L);
    }

    @DisplayName("좌석의 예약 상태를 모두 좌석된 상태로 변경한다.")
    @Test
    void reserveAllSeats() {
        // given
        Seat seat1 = Seat.builder()
                .seatNo("A-1")
                .grade(SeatGrade.A)
                .seatBookingStatus(AVAILABLE)
                .build();
        Seat seat2 = Seat.builder()
                .seatNo("B-1")
                .grade(SeatGrade.B)
                .seatBookingStatus(PROCESSING)
                .build();
        BookingSeat bookingSeat1 = BookingSeat.builder()
                .seat(seat1)
                .build();
        BookingSeat bookingSeat2 = BookingSeat.builder()
                .seat(seat2)
                .build();
        Booking booking = Booking.builder()
                .bookingSeats(List.of(bookingSeat1, bookingSeat2))
                .build();

        // when
        booking.reserveAllSeats();

        // then
        List<BookingSeat> bookingSeats = booking.getBookingSeats();
        List<Seat> seats = bookingSeats.stream()
                .map(BookingSeat::getSeat)
                .collect(Collectors.toList());

        assertThat(seats).hasSize(2)
                .extracting("seatNo", "grade", "seatBookingStatus")
                .containsExactlyInAnyOrder(
                        tuple("A-1", SeatGrade.A, SeatBookingStatus.BOOKED),
                        tuple("B-1", SeatGrade.B, SeatBookingStatus.BOOKED)
                );
    }
}