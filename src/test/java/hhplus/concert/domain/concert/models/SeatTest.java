package hhplus.concert.domain.concert.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

class SeatTest {

    @DisplayName("좌석 예약 상태를 PROCESSING 상태로 변경한다.")
    @Test
    public void markAsProcessing() {
        // given
        Seat seat = Seat.builder()
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();

        // when
        seat.markAsProcessing();

        // then
        assertThat(seat.getSeatBookingStatus()).isEqualTo(SeatBookingStatus.PROCESSING);
    }

    @DisplayName("좌석 예약 상태를 BOOKED 상태로 변경한다.")
    @Test
    public void markAsBooked() {
        // given
        Seat seat = Seat.builder()
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();

        // when
        seat.markAsBooked();

        // then
        assertThat(seat.getSeatBookingStatus()).isEqualTo(SeatBookingStatus.BOOKED);
    }

    @DisplayName("좌석의 예약 상태가 BOOKED 이면 true를 반환한다.")
    @Test
    public void isBooked() {
        // given
        Seat seat = Seat.builder()
                .seatBookingStatus(SeatBookingStatus.BOOKED)
                .build();

        // when
        boolean result = seat.isBooked();

        // when & then
        assertThat(result).isTrue();
    }

    @DisplayName("좌석의 예약 상태가 BOOKED 아니면 false를 반환한다.")
    @ParameterizedTest
    @EnumSource(value = SeatBookingStatus.class, names = { "PROCESSING", "AVAILABLE" })
    public void isBookedWithNotBookedStatus(SeatBookingStatus seatBookingStatus) {
        // given
        Seat seat = Seat.builder()
                .seatBookingStatus(seatBookingStatus)
                .build();

        // when
        boolean result = seat.isBooked();

        // when & then
        assertThat(result).isFalse();
    }
}