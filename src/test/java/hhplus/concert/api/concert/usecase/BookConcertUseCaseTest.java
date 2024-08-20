package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.controller.request.ConcertBookingRequest;
import hhplus.concert.api.concert.usecase.response.BookConcertResponse;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.support.BookingService;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.domain.concert.models.SeatBookingStatus.AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookConcertUseCaseTest {

    @Mock
    SeatReader seatReader;

    @Mock
    UserReader userReader;

    @Mock
    BookingService bookingService;

    @InjectMocks
    BookConcertUseCase bookConcertUseCase;

    @DisplayName("콘서트를 예약한다.")
    @Test
    void execute() {
        // given
        Long userId = 1L;
        String seatIds = "1,2";
        ConcertBookingRequest request = new ConcertBookingRequest(userId, seatIds);

        User user = User.builder().build();

        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 27, 16, 30, 30);
        ConcertOption concertOption = ConcertOption.builder().concertDateTime(concertDateTime).build();

        Seat seat1 = createSeat("A-1", concertOption);
        Seat seat2 = createSeat("A-2", concertOption);

        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 15, 16, 30, 30);
        Booking booking = Booking.builder()
                .concertTitle("concert")
                .bookingDateTime(bookingDateTime)
                .user(user)
                .build();

        given(userReader.getUserById(request.userId())).willReturn(user);
        given(seatReader.getSeatsByIds(request.seatIds())).willReturn(List.of(seat1, seat2));
        given(bookingService.book(user, List.of(seat1, seat2))).willReturn(booking);

        // when
        BookConcertResponse result = bookConcertUseCase.execute(request);

        // then
        verify(bookingService, times(1)).validateBookability(request.seatIds());
        assertThat(result.bookingDateTime()).isEqualTo(bookingDateTime);
        assertThat(result.concertDateTime()).isEqualTo(concertDateTime);
        assertThat(result.concertTitle()).isEqualTo("concert");
        assertThat(result.seats()).hasSize(2)
                .containsExactlyInAnyOrder(
                        "A-1", "A-2"
                );
    }

    private static Seat createSeat(String seatNo, ConcertOption concertOption) {
        return Seat.builder()
                .seatBookingStatus(AVAILABLE)
                .seatNo(seatNo)
                .concertOption(concertOption)
                .build();
    }
}