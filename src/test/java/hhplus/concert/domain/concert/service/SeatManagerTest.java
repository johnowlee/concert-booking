package hhplus.concert.domain.concert.service;

import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.concert.models.SeatGrade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static hhplus.concert.domain.concert.models.SeatBookingStatus.*;
import static hhplus.concert.domain.concert.models.SeatGrade.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.mock;

class SeatManagerTest {

    @DisplayName("모든 좌석의 예약상태를 Processing으로 변경한다.")
    @Test
    void markAllSeatsAsProcessing() {
        // given
        ConcertOption concertOption = mock(ConcertOption.class);

        Seat seat1 = createSeat("A-1", BOOKED, concertOption, A);
        Seat seat2 = createSeat("A-2", PROCESSING, concertOption, B);
        Seat seat3 = createSeat("A-3", AVAILABLE, concertOption, C);
        Seat seat4 = createSeat("A-4", BOOKED, concertOption, C);
        List<Seat> seats = List.of(seat1, seat2, seat3, seat4);

        SeatManager seatManager = new SeatManager();

        // when
        seatManager.markAllSeatsAsProcessing(seats);

        // then
        assertThat(seats).hasSize(4)
                .extracting("seatNo", "seatBookingStatus", "grade")
                .containsExactlyInAnyOrder(
                        tuple("A-1", PROCESSING, A),
                        tuple("A-2", PROCESSING, B),
                        tuple("A-3", PROCESSING, C),
                        tuple("A-4", PROCESSING, C)
                );
    }

    private static Seat createSeat(String seatNo, SeatBookingStatus booked, ConcertOption savedConcertOption, SeatGrade a) {
        return Seat.builder()
                .seatNo(seatNo)
                .seatBookingStatus(booked)
                .concertOption(savedConcertOption)
                .grade(a)
                .build();
    }
}