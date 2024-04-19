package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.dto.response.bookings.BookingsResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetBookingsByUserIdUseCase {

    private final BookingReader bookingReader;

    public BookingsResponse excute(Long userId) {
        return BookingsResponse.from(bookingReader.getBookingsByUserId(userId));
    }
}
