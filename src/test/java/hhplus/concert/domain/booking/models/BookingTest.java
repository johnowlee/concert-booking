package hhplus.concert.domain.booking.models;

import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.domain.booking.models.BookingRule.BOOKING_EXPIRY_MINUTES;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingTest {


    @DisplayName("예약이 경과시간이 만료되면 true를 반환한다.")
    @Test
    void isBookingDateTimeExpired_Success_ifExpired() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong(); //5분
        LocalDateTime bookingDateTime = LocalDateTime.now().minusMinutes(10); // 10분전
        Booking booking = Booking.builder().bookingDateTime(bookingDateTime).build();

        // when
        LocalDateTime checkTime = LocalDateTime.now();
        long passedMinutes = Duration.between(booking.getBookingDateTime(), checkTime).toMinutes();

        // then
        assertTrue(passedMinutes > expiryMinutes);
        assertTrue(booking.isBookingDateTimeExpired());
    }

    @DisplayName("예약이 경과시간이 유효하면 false를 반환한다.")
    @Test
    void isBookingDateTimeExpired_Success_ifValid() {
        // given
        long expiryMinutes = BOOKING_EXPIRY_MINUTES.toLong(); //5분
        LocalDateTime bookingDateTime = LocalDateTime.now().minusMinutes(3); // 3분전
        Booking booking = Booking.builder().bookingDateTime(bookingDateTime).build();

        // when
        LocalDateTime checkTime = LocalDateTime.now();
        long passedMinutes = Duration.between(booking.getBookingDateTime(), checkTime).toMinutes();

        // then
        assertFalse(passedMinutes > expiryMinutes);
        assertFalse(booking.isBookingDateTimeExpired());
    }

    @DisplayName("예약상태를 COMPLETE 상태로 변경에 성공한다")
    @Test
    void changeBookingStatusToComplete_Success() {
        // given
        Booking booking = Booking.builder().bookingStatus(BookingStatus.INCOMPLETE).build();

        // when
        booking.markAsComplete();

        // then
        assertThat(booking.getBookingStatus()).isEqualTo(BookingStatus.COMPLETE);
    }
}