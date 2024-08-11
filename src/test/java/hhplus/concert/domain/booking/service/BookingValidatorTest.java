package hhplus.concert.domain.booking.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.booking.models.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hhplus.concert.api.exception.code.BookingErrorCode.ALREADY_BOOKED;
import static hhplus.concert.api.exception.code.BookingErrorCode.PENDING_BOOKING;
import static hhplus.concert.domain.booking.models.BookingRule.BOOKING_EXPIRY_MINUTES;
import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static hhplus.concert.domain.booking.models.BookingStatus.INCOMPLETE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookingValidatorTest {

    @DisplayName("예약 목록 중 예약이 완료된 예약이 한 건이라도 있으면 예외가 발생한다.")
    @Test
    void checkAnyAlreadyCompleteBooking() {
        // given
        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 10, 11, 30, 30);
        Booking booking1 = Booking.builder()
                .bookingDateTime(bookingDateTime1)
                .concertTitle("IU 콘서트")
                .bookingStatus(INCOMPLETE)
                .build();

        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 13, 21, 30, 30);
        Booking booking2 = Booking.builder()
                .bookingDateTime(bookingDateTime2)
                .concertTitle("NewJeans 콘서트")
                .bookingStatus(COMPLETE)
                .build();
        List<Booking> bookings = new ArrayList<>(List.of(booking1, booking2));


        // when & then
        BookingValidator bookingValidator = new BookingValidator();
        assertThatThrownBy(() -> bookingValidator.checkAnyAlreadyCompleteBooking(bookings))
                .isInstanceOf(RestApiException.class)
                .hasMessage(ALREADY_BOOKED.getMessage());
    }

    @DisplayName("예약 목록 중 모든 예약이 예약이 완료되지 않은 상태면 예외가 발생하지 않는다.")
    @Test
    void checkAnyAlreadyCompleteBookingFromNotAnyCompleteBookings() {
        // given
        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 10, 11, 30, 30);
        Booking booking1 = Booking.builder()
                .bookingDateTime(bookingDateTime1)
                .concertTitle("IU 콘서트")
                .bookingStatus(INCOMPLETE)
                .build();

        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 13, 21, 30, 30);
        Booking booking2 = Booking.builder()
                .bookingDateTime(bookingDateTime2)
                .concertTitle("NewJeans 콘서트")
                .bookingStatus(INCOMPLETE)
                .build();
        List<Booking> bookings = new ArrayList<>(List.of(booking1, booking2));


        // when & then
        BookingValidator bookingValidator = new BookingValidator();
        bookingValidator.checkAnyAlreadyCompleteBooking(bookings);
    }

    @DisplayName("예약 목록 중 예약 시간이 유효한 예약이 한 건이라도 있으면 예외가 발생한다.")
    @Test
    void checkAnyPendingBooking() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong();
        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 11, 11, 00);
        Booking booking1 = Booking.builder()
                .bookingDateTime(bookingDateTime1)
                .build();
        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 11, 11, 01);
        Booking booking2 = Booking.builder()
                .bookingDateTime(bookingDateTime2)
                .build();
        List<Booking> bookings = List.of(booking1, booking2);

        // when & then
        BookingValidator bookingValidator = new BookingValidator();
        LocalDateTime validateTime = LocalDateTime.of(2024, 8, 11, 11, 05);
        assertThatThrownBy(() -> bookingValidator.checkAnyPendingBooking(bookings, validateTime))
                .isInstanceOf(RestApiException.class)
                .hasMessage(PENDING_BOOKING.getMessage());
    }

    @DisplayName("예약 목록 중 모든 예약의 예약 시간이 만료된 상태이면 예외가 발생하지 않는다.")
    @Test
    void checkAnyPendingBookingFromEveryBookingIsExpired() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong();
        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 11, 11, 00);
        Booking booking1 = Booking.builder()
                .bookingDateTime(bookingDateTime1)
                .build();
        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 11, 11, 01);
        Booking booking2 = Booking.builder()
                .bookingDateTime(bookingDateTime2)
                .build();
        List<Booking> bookings = List.of(booking1, booking2);

        // when & then
        BookingValidator bookingValidator = new BookingValidator();
        LocalDateTime validateTime = LocalDateTime.of(2024, 8, 11, 11, 06);
        bookingValidator.checkAnyPendingBooking(bookings, validateTime);
    }
}