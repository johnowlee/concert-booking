package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.concert.models.SeatGrade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hhplus.concert.domain.concert.models.SeatBookingStatus.*;
import static hhplus.concert.domain.concert.models.SeatGrade.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class SeatCoreReaderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    SeatCoreReaderRepository seatCoreReaderRepository;

    @Autowired
    SeatJpaRepository seatJpaRepository;

    @Autowired
    ConcertOptionJpaRepository concertOptionJpaRepository;

    @DisplayName("좌석의 ID 목록으로 좌석들을 조회한다.")
    @Test
    void getSeatsByIds() {
        // given
        ConcertOption concertOption = ConcertOption.builder()
                .place("장충체육관")
                .build();
        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);

        Seat seat1 = createSeat("A-1", BOOKED, savedConcertOption, A);
        Seat seat2 = createSeat("A-2", PROCESSING, savedConcertOption, B);
        Seat seat3 = createSeat("A-3", AVAILABLE, savedConcertOption, C);
        Seat seat4 = createSeat("A-4", BOOKED, savedConcertOption, C);
        seatJpaRepository.saveAll(List.of(seat1, seat2, seat3, seat4));

        // when
        List<Seat> result = seatCoreReaderRepository.getSeatsByIds(
                List.of(seat1.getId(),
                        seat2.getId(),
                        seat4.getId()
                )
        );

        // then
        assertThat(result).hasSize(3)
                .extracting("id", "seatNo", "seatBookingStatus", "concertOption", "grade")
                .containsExactlyInAnyOrder(
                        tuple(seat1.getId(), "A-1", BOOKED, savedConcertOption, A),
                        tuple(seat2.getId(), "A-2", PROCESSING, savedConcertOption, B),
                        tuple(seat4.getId(), "A-4", BOOKED, savedConcertOption, C)
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