package hhplus.concert.api.fakeStore.dto.response.concert;

import java.time.LocalDateTime;
import java.util.List;

public record ConcertWithSeatsResponse(long concertId, String title,
                                       LocalDateTime concertDatetime,
                                       List<SeatResponse> seats) {}
