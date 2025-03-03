package hhplus.concert.core.concert.components;

import hhplus.concert.IntegrationTestSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class SeatReaderTest extends IntegrationTestSupport {

//    @Autowired
//    SeatReader seatReader;
//
//    @Autowired
//    SeatJpaRepository seatJpaRepository;
//
//    @Autowired
//    ConcertOptionJpaRepository concertOptionJpaRepository;
//
//    @DisplayName("좌석의 ID 목록으로 좌석들을 조회한다.")
//    @Test
//    void getSeatsByIds() {
//        // given
//        ConcertOption concertOption = ConcertOption.builder()
//                .place("장충체육관")
//                .build();
//        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);
//
//        Seat seat1 = createSeat("A-1", BOOKED, savedConcertOption, A);
//        Seat seat2 = createSeat("A-2", PROCESSING, savedConcertOption, B);
//        Seat seat3 = createSeat("A-3", AVAILABLE, savedConcertOption, C);
//        Seat seat4 = createSeat("A-4", BOOKED, savedConcertOption, C);
//        seatJpaRepository.saveAll(List.of(seat1, seat2, seat3, seat4));
//
//        // when
//        List<Seat> result = seatReader.getSeatsByIds(
//                List.of(seat1.getId(),
//                        seat2.getId(),
//                        seat4.getId()
//                )
//        );
//
//        // then
//        assertThat(result).hasSize(3)
//                .extracting("id", "seatNo", "seatBookingStatus", "concertOption", "grade")
//                .containsExactlyInAnyOrder(
//                        tuple(seat1.getId(), "A-1", BOOKED, savedConcertOption, A),
//                        tuple(seat2.getId(), "A-2", PROCESSING, savedConcertOption, B),
//                        tuple(seat4.getId(), "A-4", BOOKED, savedConcertOption, C)
//                );
//    }
//
//    @DisplayName("콘서트옵션 ID로 좌석 목록을 조회한다.")
//    @Test
//    void getSeatsByConcertOptionId() {
//        // given
//        ConcertOption concertOption = ConcertOption.builder()
//                .place("장충체육관")
//                .build();
//        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);
//
//        Seat seat1 = createSeat("A-1", BOOKED, savedConcertOption, A);
//        Seat seat2 = createSeat("A-2", PROCESSING, savedConcertOption, B);
//        Seat seat3 = createSeat("A-3", AVAILABLE, savedConcertOption, C);
//        Seat seat4 = createSeat("A-4", BOOKED, savedConcertOption, C);
//        seatJpaRepository.saveAll(List.of(seat1, seat2, seat3, seat4));
//
//        // when
//        List<Seat> result = seatReader.getSeatsByConcertOptionId(savedConcertOption.getId());
//
//        // then
//        assertThat(result).hasSize(4)
//                .extracting("id", "seatNo", "seatBookingStatus", "concertOption", "grade")
//                .containsExactlyInAnyOrder(
//                        tuple(seat1.getId(), "A-1", BOOKED, savedConcertOption, A),
//                        tuple(seat2.getId(), "A-2", PROCESSING, savedConcertOption, B),
//                        tuple(seat3.getId(), "A-3", AVAILABLE, savedConcertOption, C),
//                        tuple(seat4.getId(), "A-4", BOOKED, savedConcertOption, C)
//                );
//    }
//
//    private static Seat createSeat(String seatNo, SeatBookingStatus booked, ConcertOption savedConcertOption, SeatGrade a) {
//        return Seat.builder()
//                .seatNo(seatNo)
//                .seatBookingStatus(booked)
//                .concertOption(savedConcertOption)
//                .grade(a)
//                .build();
//    }
}