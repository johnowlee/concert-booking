package hhplus.concert.core.booking.domain.service;

import hhplus.concert.core.booking.domain.model.BookingSeat;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.BookingErrorCode;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.port.BookingQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingQueryService {

    private final BookingQueryPort bookingQueryPort;

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingQueryPort.getBookingsByUserId(userId);
    }

    public Booking getBookingById(Long id) {
        return bookingQueryPort.getBookingById(id)
                .orElseThrow(() -> new RestApiException(BookingErrorCode.NOT_FOUND_BOOKING));
    }

    public List<BookingSeat> getBookingSeatsBySeatIds (List<Long> seatIds) {
        return bookingQueryPort.getBookingSeatsBySeatIds(seatIds);
    }
}
