package hhplus.concert.domain.booking.models;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BookingErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingTest {

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

    @DisplayName("예약 가능 시간이 만료되었으면, validatePending 검증에서 아무일도 일어나지 않는다.")
    @Test
    void validatePending_ShouldNotThrowException_WhenBookingIsNotPending() {
        // given
        Booking booking = spy(Booking.class);
        LocalDateTime bookingDateTime = LocalDateTime.now().minusMinutes(40);
        when(booking.getBookingDateTime()).thenReturn(bookingDateTime);

        // when & then
        booking.validatePending();
    }

    @DisplayName("예약 가능 시간이 만료되지 않았으면, validatePending 검증에서 예외를 터트린다.")
    @Test
    void validatePending_ShouldThrowException_WhenBookingIsPending() {
        // given
        Booking booking = spy(Booking.class);
        LocalDateTime bookingDateTime = LocalDateTime.now().minusMinutes(4);
        when(booking.getBookingDateTime()).thenReturn(bookingDateTime);

        // when & then
        RestApiException exception = assertThrows(RestApiException.class, () -> booking.validatePending());
        assertEquals(BookingErrorCode.PENDING_BOOKING, exception.getErrorCode());
    }
}