package hhplus.concert.api.fakeStore.dto.response.booking;

import java.time.LocalDateTime;
import java.util.List;

public record BookingConcert(String title,
                             LocalDateTime concertDatetime,
                             List<String> seats) {}
