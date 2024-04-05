package hhplus.concert.api.mock.dto.response.concert;

import java.time.LocalDateTime;
import java.util.List;

public record ConcertWithSeatsResponse(long concertId, String title,
                                       LocalDateTime concertDateTime,
                                       List<SeatResponse> seats) {}
