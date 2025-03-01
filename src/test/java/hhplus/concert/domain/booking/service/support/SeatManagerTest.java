package hhplus.concert.domain.booking.service.support;

class SeatManagerTest {

//    @DisplayName("모든 좌석의 예약상태를 Processing으로 변경한다.")
//    @Test
//    void markAllSeatsAsProcessing() {
//        // given
//        ConcertOption concertOption = mock(ConcertOption.class);
//
//        Seat seat1 = createSeat("A-1", BOOKED, concertOption, A);
//        Seat seat2 = createSeat("A-2", PROCESSING, concertOption, B);
//        Seat seat3 = createSeat("A-3", AVAILABLE, concertOption, C);
//        Seat seat4 = createSeat("A-4", BOOKED, concertOption, C);
//        List<Seat> seats = List.of(seat1, seat2, seat3, seat4);
//
//        SeatManager seatManager = new SeatManager();
//
//        // when
//        seatManager.markAllSeatsAsProcessing(seats);
//
//        // then
//        assertThat(seats).hasSize(4)
//                .extracting("seatNo", "seatBookingStatus", "grade")
//                .containsExactlyInAnyOrder(
//                        tuple("A-1", PROCESSING, A),
//                        tuple("A-2", PROCESSING, B),
//                        tuple("A-3", PROCESSING, C),
//                        tuple("A-4", PROCESSING, C)
//                );
//    }
//
//    @DisplayName("예약하는 좌석으로부터 콘서트의 타이틀을 조회한다.")
//    @Test
//    void getConcertTitleBy() {
//        // given
//        Concert concert = Concert.builder()
//                .title("IU 콘서트")
//                .build();
//        ConcertOption concertOption = ConcertOption.builder()
//                .concert(concert)
//                .build();
//        Seat seat1 = createSeat("A-1", BOOKED, concertOption, A);
//        Seat seat2 = createSeat("A-2", PROCESSING, concertOption, B);
//        List<Seat> seats = List.of(seat1, seat2);
//
//        // when
//        SeatManager seatManager = new SeatManager();
//        String result = seatManager.getConcertTitleFrom(seats);
//
//        // then
//        assertThat(result).isEqualTo("IU 콘서트");
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