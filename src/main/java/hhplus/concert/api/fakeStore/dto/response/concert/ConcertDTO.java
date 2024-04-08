package hhplus.concert.api.fakeStore.dto.response.concert;

import java.time.LocalDateTime;
import java.util.List;

public record ConcertDTO(long concertId,
                         String title,
                         LocalDateTime concertDateTime,
                         List<SeatDTO> seats) {}
