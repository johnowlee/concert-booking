package hhplus.concert.domain.booking.service.support;

class SeatValidatorTest {

//    @DisplayName("좌석 목록 중 좌석의 예약상태가 한 건 이라도 예약된 좌석이 있으면 예외가 발생한다.")
//    @Test
//    void checkAnyBookedSeat() {
//        // given
//        Seat seat1 = Seat.builder()
//                .seatBookingStatus(SeatBookingStatus.BOOKED)
//                .build();
//        Seat seat2 = Seat.builder()
//                .seatBookingStatus(SeatBookingStatus.PROCESSING)
//                .build();
//
//        List<Seat> seats = List.of(seat1, seat2);
//
//        // when & then
//        SeatValidator seatValidator = new SeatValidator();
//        assertThatThrownBy(() -> seatValidator.checkAnyBookedSeat(seats))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(ALREADY_BOOKED.getMessage());
//    }
//
//    @DisplayName("좌석 목록 중 모든 좌석의 예약상태가 예약되지 않은 상태이면 예외가 발생하지 않는다.")
//    @Test
//    void checkAnyBookedSeatFromEverySeatIsNotBooked() {
//        // given
//        Seat seat1 = Seat.builder()
//                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
//                .build();
//        Seat seat2 = Seat.builder()
//                .seatBookingStatus(SeatBookingStatus.PROCESSING)
//                .build();
//
//        List<Seat> seats = List.of(seat1, seat2);
//
//        // when & then
//        SeatValidator seatValidator = new SeatValidator();
//        seatValidator.checkAnyBookedSeat(seats);
//    }

}