package hhplus.concert.application.user;

import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetBookingsUseCase {

    private final BookingReader bookingReader;

    public List<Booking> execute(Long userId) {
        return bookingReader.getBookingsByUserId(userId);
    }
}