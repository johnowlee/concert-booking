package hhplus.concert.domain.booking.models;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BookingErrorCode;
import hhplus.concert.domain.concert.models.SeatPriceByGrade;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        booking.validatePendingBooking();
    }

    @DisplayName("예약 가능 시간이 만료되지 않았으면, validatePending 검증에서 예외를 터트린다.")
    @Test
    void validatePending_ShouldThrowException_WhenBookingIsPending() {
        // given
        Booking booking = spy(Booking.class);
        LocalDateTime bookingDateTime = LocalDateTime.now().minusMinutes(4);
        when(booking.getBookingDateTime()).thenReturn(bookingDateTime);

        // when & then
        RestApiException exception = assertThrows(RestApiException.class, () -> booking.validatePendingBooking());
        assertEquals(BookingErrorCode.PENDING_BOOKING, exception.getErrorCode());
    }

    @DisplayName("예약좌석 수와 좌석가격의 곱의 값을 리턴한다.")
    @Test
    void getTotalPrice_ReturnBookingSeatsSizeTimesSeatPrice() {
        // given
        Booking booking = spy(Booking.class);
        List<BookingSeat> mockedSeats = Arrays.asList(
                mock(BookingSeat.class),
                mock(BookingSeat.class),
                mock(BookingSeat.class)
        );
        given(booking.getBookingSeats()).willReturn(mockedSeats);
        int seatPrice = SeatPriceByGrade.A.getValue();

        // when
        int actual = booking.getTotalPrice();

        // when & then
        assertEquals((3 * seatPrice), actual);
    }

    @DisplayName("userId가 같지 않다면, 예외를 터트린다.")
    @Test
    void validatePayer_ShouldThrowException_WhenUserIdNotSame() {
        // given
        User user = User.builder().id(1L).build();
        Booking booking = Booking.builder().user(user).build();

        // when & then
        RestApiException exception = assertThrows(RestApiException.class, () -> booking.validatePayer(2L));
        assertEquals(BookingErrorCode.INVALID_PAYER, exception.getErrorCode());
    }

    @DisplayName("userId가 같다면, 아무일도 일어나지 않는다.")
    @Test
    void validatePayer_ShouldNotThrowException_WhenUserIdSame() {
        // given
        User user = User.builder().id(1L).build();
        Booking booking = Booking.builder().user(user).build();

        // when & then
        booking.validatePayer(1L);
    }
}