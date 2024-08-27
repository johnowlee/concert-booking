package hhplus.concert.api.booking.usecase.response;

import hhplus.concert.api.common.response.BookingResponse;
import hhplus.concert.domain.booking.models.Booking;

import java.util.List;
import java.util.stream.Collectors;

public record BookingsResponse(List<BookingResponse> bookings) {

    public static BookingsResponse from(List<Booking> bookings) {
        return new BookingsResponse(
                bookings.stream()
                    .map(booking -> BookingResponse.from(booking))
                    .collect(Collectors.toList())
        );
    }
}
