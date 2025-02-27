package hhplus.concert.api.user.mapper;

import hhplus.concert.api.user.response.BookingResponse;
import hhplus.concert.domain.booking.models.Booking;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingResponseMapper {

    public BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getBookingStatus(),
                booking.getBookingDateTime(),
                booking.getConcertTitle()
        );
    }

    public List<BookingResponse> toBookingResponseList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toBookingResponse)
                .collect(Collectors.toList());
    }
}
