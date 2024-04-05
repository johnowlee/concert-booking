package hhplus.concert.api.dto.response.concert;

import java.time.LocalDateTime;
import java.util.List;

public record ConcertsResponse(List<ConcertResponse> concerts) {}
