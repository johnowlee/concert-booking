package hhplus.concert.api.fakeStore.dto.response.concert;

import hhplus.concert.entities.booking.BookingStatus;

public record SeatResponse(long seatId, String seatNo, BookingStatus status) {}
