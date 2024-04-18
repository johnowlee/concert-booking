package hhplus.concert.api.fakeStore.dto.response.booking;

import hhplus.concert.domain.booking.models.BookingStatus;

import java.time.LocalDateTime;

public record BookingDTO(long bookingId,
                         BookingStatus bookingStatus,
                         LocalDateTime bookingDatetime) {}
