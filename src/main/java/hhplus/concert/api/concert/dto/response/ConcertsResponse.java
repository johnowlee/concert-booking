package hhplus.concert.api.concert.dto.response;

import hhplus.concert.domain.concert.models.ConcertEntity;

import java.util.List;

public record ConcertsResponse(List<ConcertEntity> concertEntities) {
}
