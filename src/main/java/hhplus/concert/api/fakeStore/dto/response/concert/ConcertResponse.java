package hhplus.concert.api.fakeStore.dto.response.concert;

import java.time.LocalDateTime;

public record ConcertResponse(long concertId, String title,
                              LocalDateTime concertDatetime,
                              int availableSeatsCount) {}
