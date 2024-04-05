package hhplus.concert.api.mock.dto.response.concert;

import hhplus.concert.domain.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ConcertDTO(long concertId,
                         String title,
                         LocalDateTime concertDateTime,
                         List<SeatDTO> seats) {}
