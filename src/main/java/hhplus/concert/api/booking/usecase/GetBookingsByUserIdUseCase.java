package hhplus.concert.api.booking.usecase;

import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetBookingsByUserIdUseCase {

    private final BookingReader bookingReader;

    public List<Booking> excute(Long userId) {
        return bookingReader.getBookingsByUserId(userId);
    }
}
