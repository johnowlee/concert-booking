package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.dto.response.booking.BookingResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetBookingByIdUseCase {

    private final BookingReader bookingReader;

    public BookingResponse excute(Long id) {
        return BookingResponse.from(bookingReader.getBookingById(id));
    }
}
