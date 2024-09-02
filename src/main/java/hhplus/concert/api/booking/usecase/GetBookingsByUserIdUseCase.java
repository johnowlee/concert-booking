package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.usecase.response.BookingsResponse;
import hhplus.concert.api.common.UseCase;
import hhplus.concert.domain.booking.components.BookingReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class GetBookingsByUserIdUseCase {

    private final BookingReader bookingReader;

    public BookingsResponse execute(Long userId) {
        return BookingsResponse.from(bookingReader.getBookingsByUserId(userId));
    }
}
