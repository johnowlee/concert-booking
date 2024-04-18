package hhplus.concert.api.fakeStore.dto.response.concert;

import hhplus.concert.domain.booking.models.BookingStatus;

public record SeatResponse(long seatId, String seatNo, BookingStatus status) {}
