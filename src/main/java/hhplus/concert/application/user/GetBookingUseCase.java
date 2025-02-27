package hhplus.concert.application.user;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class GetBookingUseCase {

    private final BookingReader bookingReader;

    public Booking execute(Long userId, Long bookingId) {
        return bookingReader.getBookingById(bookingId);
    }
}
