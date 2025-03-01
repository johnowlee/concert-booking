package hhplus.concert.application.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookConcertUseCaseTest {

//    @Mock
//    SeatReader seatReader;
//
//    @Mock
//    UserReader userReader;
//
//    @InjectMocks
//    BookConcertUseCase bookConcertUseCase;
//
//    @DisplayName("콘서트를 예약한다.")
//    @Test
//    void execute() {
//        // given
//        Long userId = 1L;
//        List<Long> seatIds = new ArrayList<>();
//        seatIds.add(1L);
//        seatIds.add(2L);
//        ConcertBookingDto dto = new ConcertBookingDto(seatIds);
//
//        User user = User.builder().build();
//
//        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 27, 16, 30, 30);
//        ConcertOption concertOption = ConcertOption.builder().concertDateTime(concertDateTime).build();
//
//        Seat seat1 = createSeat("A-1", concertOption);
//        Seat seat2 = createSeat("A-2", concertOption);
//
//
//        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 15, 16, 30, 30);
//        Booking booking = Booking.builder()
//                .concertTitle("concert")
//                .bookingDateTime(bookingDateTime)
//                .user(user)
//                .build();
//
//        BookingSeat bookingSeat1 = createBookingSeat(1L, seat1);
//        bookingSeat1.setBooking(booking);
//
//        BookingSeat bookingSeat2 = createBookingSeat(2L, seat2);
//        bookingSeat2.setBooking(booking);
//
//        given(userReader.getUserById(userId)).willReturn(user);
//        given(seatReader.getSeatsByIds(dto.seatIds())).willReturn(List.of(seat1, seat2));
//        given(bookingService.book(user, List.of(seat1, seat2))).willReturn(booking);
//
//        // when
//        Booking result = bookConcertUseCase.execute(userId, dto);
//
//        // then
//        verify(bookingService, times(1)).validateBookability(dto.seatIds());
//        assertThat(result.getBookingDateTime()).isEqualTo(bookingDateTime);
//        assertThat(result.getConcertTitle()).isEqualTo("concert");
//    }
//
//    private static Seat createSeat(String seatNo, ConcertOption concertOption) {
//        return Seat.builder()
//                .seatBookingStatus(AVAILABLE)
//                .seatNo(seatNo)
//                .concertOption(concertOption)
//                .build();
//    }
//
//    private BookingSeat createBookingSeat(Long id, Seat seat) {
//        BookingSeat bookingSeat = BookingSeat.builder().seat(seat).build();
//        ReflectionTestUtils.setField(bookingSeat, "id", id);
//        return bookingSeat;
//    }
}