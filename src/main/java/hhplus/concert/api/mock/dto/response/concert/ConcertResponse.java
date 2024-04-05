package hhplus.concert.api.mock.dto.response.concert;

import java.time.LocalDateTime;
import java.util.List;

public record ConcertResponse(long concertId, String title,
                              LocalDateTime concertDatetime,
                              int availableSeatsCount) {}
