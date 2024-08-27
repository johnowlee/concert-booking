package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.common.response.BookingResponse;
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

import java.util.List;

import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetBookingByIdUseCaseTest {

    @Mock
    BookingReader bookingReader;

    @InjectMocks
    GetBookingByIdUseCase getBookingByIdUseCase;

    @DisplayName("예약 아이디로 예약을 조회한다.")
    @Test
    void execute() {
        // given
        Booking booking = mock(Booking.class);
        User user = mock(User.class);
        BookingSeat bookingSeat = mock(BookingSeat.class);
        Seat seat = mock(Seat.class);
        ConcertOption concertOption = mock(ConcertOption.class);

        given(seat.getConcertOption()).willReturn(concertOption);
        given(bookingSeat.getSeat()).willReturn(seat);

        given(booking.getId()).willReturn(1L);
        given(booking.getUser()).willReturn(user);
        given(booking.getBookingSeats()).willReturn(List.of(bookingSeat));
        given(booking.getBookingStatus()).willReturn(COMPLETE);

        given(bookingReader.getBookingById(1L)).willReturn(booking);

        // when
        BookingResponse result = getBookingByIdUseCase.execute(1L);

        // then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.bookingStatus()).isEqualTo(COMPLETE);
    }
}