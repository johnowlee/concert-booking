package hhplus.concert.api.dto.response.concert;

import hhplus.concert.domain.model.concert.Seat;
import hhplus.concert.domain.model.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record SeatResponse(long seatId, String seatNo, BookingStatus status) {}
