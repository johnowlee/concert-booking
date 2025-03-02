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

    public List<Booking> findBookingsByUserId(Long userId) {
        return bookingQueryPort.findBookingsByUserId(userId);
    }

    public Booking getBookingById(Long id) {
        return bookingQueryPort.findBookingById(id)
                .orElseThrow(() -> new RestApiException(BookingErrorCode.NOT_FOUND_BOOKING));
    }

    public List<BookingSeat> findBookingSeatsBySeatIds(List<Long> seatIds) {
        return bookingQueryPort.findBookingSeatsBySeatIds(seatIds);
    }
}
