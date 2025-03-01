package hhplus.concert.representer.api.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.core.booking.domain.model.BookingStatus;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record BookingResponse(Long id, BookingStatus bookingStatus, LocalDateTime bookingDateTime, String concertTitle) {}