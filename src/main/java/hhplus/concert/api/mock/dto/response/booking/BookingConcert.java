package hhplus.concert.api.mock.dto.response.booking;

import java.time.LocalDateTime;
import java.util.List;

public record BookingConcert(String title,
                             LocalDateTime concertDatetime,
                             List<String> seats) {}
