package hhplus.concert.api.mock.dto.response.booking;

import hhplus.concert.domain.booking.model.BookingStatus;

import java.time.LocalDateTime;

public record BookingDTO(long bookingId,
                         BookingStatus bookingStatus,
                         LocalDateTime bookingDatetime) {}
