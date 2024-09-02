package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.common.response.BookingResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class GetBookingByIdUseCase {

    private final BookingReader bookingReader;

    public BookingResponse execute(Long id) {
        return BookingResponse.from(bookingReader.getBookingById(id));
    }
}
