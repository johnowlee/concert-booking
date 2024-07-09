package hhplus.concert.domain.concert.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SeatTest {

    @Test
    public void testMarkAsBooked() {
        // given
        Seat seat = Seat.builder()
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();

        // when
        seat.markAsBooked();

        // then
        Assertions.assertEquals(SeatBookingStatus.BOOKED, seat.getSeatBookingStatus());
    }

    @Test
    public void testIsBooked() {
        // expected
        Seat seat = Seat.builder()
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();

        // actual
        Assertions.assertFalse(seat.isBooked());


        // when
        seat.markAsBooked();

        // then
        Assertions.assertTrue(seat.isBooked());
    }

    @Test
    public void testMarkAsProcessing() {
        // given
        Seat seat = Seat.builder()
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();

        // when
        seat.markAsProcessing();

        // then
        Assertions.assertEquals(SeatBookingStatus.PROCESSING, seat.getSeatBookingStatus());
    }
}