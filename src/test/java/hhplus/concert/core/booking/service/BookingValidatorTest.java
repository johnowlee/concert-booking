package hhplus.concert.core.booking.service;

class BookingValidatorTest {

//    @DisplayName("예약 목록 중 예약이 완료된 예약이 한 건이라도 있으면 예외가 발생한다.")
//    @Test
//    void checkAnyAlreadyCompleteBooking() {
//        // given
//        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 10, 11, 30);
//        Booking booking1 = createBooking(1L, bookingDateTime1, INCOMPLETE, "IU 콘서트");
//
//        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 13, 21, 35);
//        Booking booking2 = createBooking(2L, bookingDateTime2, COMPLETE, "NewJeans 콘서트");
//
//        Set<Booking> bookings = Set.of(booking1, booking2);
//
//
//        // when & then
//        BookingValidator bookingValidator = new BookingValidator();
//        assertThatThrownBy(() -> bookingValidator.checkAnyAlreadyCompleteBooking(bookings))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(ALREADY_BOOKED.getMessage());
//    }
//
//    @DisplayName("예약 목록 중 모든 예약이 예약이 완료되지 않은 상태면 예외가 발생하지 않는다.")
//    @Test
//    void checkAnyAlreadyCompleteBookingFromNotAnyCompleteBookings() {
//        // given
//        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 10, 11, 30);
//        Booking booking1 = createBooking(1L, bookingDateTime1, INCOMPLETE, "IU 콘서트");
//
//        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 13, 21, 35);
//        Booking booking2 = createBooking(2L, bookingDateTime2, INCOMPLETE, "NewJeans 콘서트");
//
//        Set<Booking> bookings = Set.of(booking1, booking2);
//
//
//        // when & then
//        BookingValidator bookingValidator = new BookingValidator();
//        bookingValidator.checkAnyAlreadyCompleteBooking(bookings);
//    }
//
//    @DisplayName("예약 목록 중 예약 시간이 유효한 예약이 한 건이라도 있으면 예외가 발생한다.")
//    @Test
//    void checkAnyPendingBooking() {
//        // given
//        long expiryMinutes = ALLOWED_MINUTES.getMinutes();
//        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 11, 11, 00);
//        Booking booking1 = createBooking(1L, bookingDateTime1, INCOMPLETE, "IU 콘서트");
//
//        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 11, 15, 01);
//        Booking booking2 = createBooking(2L, bookingDateTime2, INCOMPLETE, "NewJeans 콘서트");
//
//        Set<Booking> bookings = Set.of(booking1, booking2);
//
//        LocalDateTime verificationTime = bookingDateTime2.plusMinutes(ALLOWED_MINUTES.getMinutes() -1);
//
//        // when & then
//        BookingValidator bookingValidator = new BookingValidator();
//        assertThatThrownBy(() -> bookingValidator.checkAnyPendingBooking(bookings, verificationTime))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(PENDING_BOOKING.getMessage());
//    }
//
//    @DisplayName("예약 목록 중 모든 예약의 예약 시간이 만료된 상태이면 예외가 발생하지 않는다.")
//    @Test
//    void checkAnyPendingBookingFromEveryBookingIsExpired() {
//        // given
//        long expiryMinutes = ALLOWED_MINUTES.getMinutes();
//        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 11, 11, 00);
//        Booking booking1 = createBooking(1L, bookingDateTime1, INCOMPLETE, "IU 콘서트");
//
//        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 11, 15, 01);
//        Booking booking2 = createBooking(2L, bookingDateTime1, INCOMPLETE, "NewJeans 콘서트");
//
//        Set<Booking> bookings = Set.of(booking1, booking2);
//
//        LocalDateTime verificationTime = bookingDateTime2.plusMinutes(ALLOWED_MINUTES.getMinutes() -1);
//
//        // when & then
//        BookingValidator bookingValidator = new BookingValidator();
//        bookingValidator.checkAnyPendingBooking(bookings, verificationTime);
//    }
//
//    private static Booking createBooking(long id, LocalDateTime bookingDateTime, BookingStatus bookingStatus, String concertTitle) {
//        Booking booking = Booking.builder()
//                .bookingDateTime(bookingDateTime)
//                .concertTitle(concertTitle)
//                .bookingStatus(bookingStatus)
//                .build();
//        ReflectionTestUtils.setField(booking, "id", id);
//        return booking;
//    }
}