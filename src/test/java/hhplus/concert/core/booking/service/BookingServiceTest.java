package hhplus.concert.core.booking.service;

import hhplus.concert.IntegrationTestSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class BookingServiceTest extends IntegrationTestSupport {

//    @Autowired
//    BookingService bookingService;
//
//    @MockBean
//    ClockManager clockManager;
//
//    @MockBean
//    BookingSeatReader bookingSeatReader;
//
//    @MockBean
//    BookingValidator bookingValidator;
//
//    @MockBean
//    SeatValidator seatValidator;
//
//    @Autowired
//    ConcertJpaRepository concertJpaRepository;
//
//    @Autowired
//    SeatJpaRepository seatJpaRepository;
//
//    @Autowired
//    ConcertOptionJpaRepository concertOptionJpaRepository;
//
//    @Autowired
//    UserJpaRepository userJpaRepository;
//
//    @Autowired
//    BookingJpaRepository bookingJpaRepository;
//
//    @Autowired
//    BookingSeatJpaRepository bookingSeatJpaRepository;
//
//    @DisplayName("콘서트를 예약한다.")
//    @Test
//    void book() {
//        // given
//        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 8, 15, 25);
//        given(clockManager.getNowDateTime()).willReturn(bookingDateTime);
//
//        Concert concert = Concert.builder()
//                .title("IU 콘서트")
//                .build();
//        Concert savedConcert = concertJpaRepository.save(concert);
//
//        ConcertOption concertOption = ConcertOption.builder()
//                .concert(savedConcert)
//                .build();
//        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);
//
//        Seat seat1 = createSeat("A-1", BOOKED, savedConcertOption, A);
//        Seat seat2 = createSeat("A-2", PROCESSING, savedConcertOption, B);
//        Seat seat3 = createSeat("A-3", AVAILABLE, savedConcertOption, C);
//        Seat seat4 = createSeat("A-4", BOOKED, savedConcertOption, C);
//        List<Seat> savedSeats = seatJpaRepository.saveAll(List.of(seat1, seat2, seat3, seat4));
//
//        User user = User.builder()
//                .name("jon")
//                .build();
//        User savedUser = userJpaRepository.save(user);
//
//        // when
//        Booking result = bookingService.book(savedUser, savedSeats);
//
//        // then
//        assertThat(result)
//                .extracting(
//                        Booking::getConcertTitle,
//                        Booking::getBookingDateTime,
//                        Booking::getUser
//                )
//                .contains("IU 콘서트", bookingDateTime, savedUser);
//
//        List<BookingSeat> bookingSeats = bookingSeatJpaRepository.findAll();
//        assertThat(bookingSeats).hasSize(4)
//                .extracting(
//                        BookingSeat::getBooking,
//                        BookingSeat::getSeat
//                )
//                .containsExactlyInAnyOrder(
//                        tuple(result, seat1),
//                        tuple(result, seat2),
//                        tuple(result, seat3),
//                        tuple(result, seat4)
//                );
//
//        List<Seat> seats = seatJpaRepository.findAll();
//        assertThat(seats).hasSize(4)
//                .extracting(
//                        Seat::getSeatNo,
//                        Seat::getSeatBookingStatus,
//                        Seat::getConcertOption,
//                        Seat::getGrade
//                )
//                .containsExactlyInAnyOrder(
//                        tuple("A-1", PROCESSING, savedConcertOption, A),
//                        tuple("A-2", PROCESSING, savedConcertOption, B),
//                        tuple("A-3", PROCESSING, savedConcertOption, C),
//                        tuple("A-4", PROCESSING, savedConcertOption, C)
//                );
//    }
//
//    @DisplayName("예약 가능 여부를 검증한다.")
//    @Test
//    void validateBookability3() {
//        // given
//        List<Long> seatIds = List.of(1L, 2L);
//        BookingSeat bookingSeat1 = mock(BookingSeat.class);
//        BookingSeat bookingSeat2 = mock(BookingSeat.class);
//        List<BookingSeat> bookingSeats = List.of(bookingSeat1, bookingSeat2);
//
//        Booking booking1 = mock(Booking.class);
//        Booking booking2 = mock(Booking.class);
//        Set<Booking> bookings = Set.of(booking1, booking2);
//
//        given(bookingSeat1.getBooking()).willReturn(booking1);
//        given(bookingSeat2.getBooking()).willReturn(booking2);
//
//        given(bookingSeatReader.getBookingSeatsBySeatIds(seatIds)).willReturn(bookingSeats);
//
//        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 11, 11, 00);
//        given(clockManager.getNowDateTime()).willReturn(bookingDateTime);
//
//        // when
//        bookingService.validateBookability(seatIds);
//
//        // then
//        then(bookingValidator).should(times(1)).checkAnyAlreadyCompleteBooking(bookings);
//        then(bookingValidator).should(times(1)).checkAnyPendingBooking(bookings, bookingDateTime);
//        then(seatValidator).should(times(1)).checkAnyBookedSeat(anyList());
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