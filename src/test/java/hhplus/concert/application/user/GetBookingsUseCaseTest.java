package hhplus.concert.application.user;

import hhplus.concert.application.user.usecase.GetBookingsUseCase;
import hhplus.concert.core.booking.domain.service.BookingQueryService;
import hhplus.concert.core.booking.domain.model.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.core.booking.domain.model.BookingStatus.COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetBookingsUseCaseTest {

    @Mock
    BookingQueryService bookingQueryService;

    @InjectMocks
    GetBookingsUseCase getBookingsUseCase;

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

        given(bookingQueryService.getBookingsByUserId(1L)).willReturn(List.of(booking));

        // when
        List<Booking> result = getBookingsUseCase.execute(1L);

        // then
        assertThat(result).hasSize(1)
                .extracting("id", "bookingStatus", "bookingDateTime", "concertTitle")
                .contains(
                        tuple(1L, COMPLETE, bookingDateTime, "아이유 콘서트")
                );
    }
}