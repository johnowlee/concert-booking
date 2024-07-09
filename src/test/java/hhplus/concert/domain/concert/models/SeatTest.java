package hhplus.concert.domain.concert.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SeatTest {

    @Test
    public void testChangeBookingStatus() {
        // given
        Seat seat = Seat.builder()
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();

        // when
        seat.changeBookingStatus(SeatBookingStatus.BOOKED);

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
        seat.changeBookingStatus(SeatBookingStatus.BOOKED);

        // then
        Assertions.assertTrue(seat.isBooked());
    }
}