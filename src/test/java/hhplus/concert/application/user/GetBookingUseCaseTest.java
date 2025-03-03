package hhplus.concert.application.user;

import hhplus.concert.application.user.usecase.GetBookingUseCase;
import hhplus.concert.core.booking.domain.service.BookingQueryService;
import hhplus.concert.core.booking.domain.model.Booking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static hhplus.concert.core.booking.domain.model.BookingStatus.COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GetBookingUseCaseTest {

    @Mock
    BookingQueryService bookingQueryService;

    @InjectMocks
    GetBookingUseCase getBookingUseCase;

    @DisplayName("예약 아이디로 예약을 조회한다.")
    @Test
    void execute() {
        // given
        Booking booking = mock(Booking.class);

        given(booking.getId()).willReturn(1L);
        given(booking.getBookingStatus()).willReturn(COMPLETE);

        given(bookingQueryService.getBookingById(1L)).willReturn(booking);

        // when
        Booking result = getBookingUseCase.execute(1L, 1L);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getBookingStatus()).isEqualTo(COMPLETE);
    }
}