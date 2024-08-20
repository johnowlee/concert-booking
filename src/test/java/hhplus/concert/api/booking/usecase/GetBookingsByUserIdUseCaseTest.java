package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.usecase.response.BookingsResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetBookingsByUserIdUseCaseTest {

    @Mock
    BookingReader bookingReader;

    @InjectMocks
    GetBookingsByUserIdUseCase getBookingsByUserIdUseCase;

    @DisplayName("유저 아이디로 예약 목록을 조회한다.")
    @Test
    void execute() {
        // given
        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 10, 12, 00);
        Booking booking = mock(Booking.class);
        given(booking.getId()).willReturn(1L);
        given(booking.getBookingStatus()).willReturn(COMPLETE);
        given(booking.getBookingDateTime()).willReturn(bookingDateTime);
        given(booking.getConcertTitle()).willReturn("아이유 콘서트");
        given(booking.getUser()).willReturn(mock(User.class));

        BookingSeat bookingSeat = mock(BookingSeat.class);
        given(booking.getBookingSeats()).willReturn(List.of(bookingSeat));

        Seat seat = mock(Seat.class);
        given(bookingSeat.getSeat()).willReturn(seat);

        ConcertOption concertOption = mock(ConcertOption.class);
        given(seat.getConcertOption()).willReturn(concertOption);

        given(bookingReader.getBookingsByUserId(1L)).willReturn(List.of(booking));

        // when
        BookingsResponse result = getBookingsByUserIdUseCase.execute(1L);

        // then
        assertThat(result.bookings()).hasSize(1)
                .extracting("id", "bookingStatus", "bookingDateTime", "concertTitle")
                .contains(
                        tuple(1L, COMPLETE, bookingDateTime, "아이유 콘서트")
                );
    }
}