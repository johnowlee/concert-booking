package hhplus.concert.api.booking.dto.response.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.models.BookingStatus;
import hhplus.concert.domain.user.models.User;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record BookingResponse(
        Long id,
        BookingStatus bookingStatus,
        LocalDateTime bookingDateTime,
        String concertTitle,
        User user,
        List<BookingSeat> bookingSeats
) {}
