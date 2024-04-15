package hhplus.concert.api.concert.dto.response;

import hhplus.concert.entities.concert.ConcertEntity;

import java.util.List;

public record ConcertsResponse(List<ConcertEntity> concertEntities) {
}
