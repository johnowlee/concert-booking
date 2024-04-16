package hhplus.concert.api.concert.dto.response;

import hhplus.concert.api.concert.dto.ConcertDto;
import hhplus.concert.domain.concert.models.Concert;

import java.util.List;

public record ConcertsResponse(List<ConcertDto> concerts) {
    public static ConcertsResponse from(List<ConcertDto> concerts) {
        return new ConcertsResponse(concerts);
    }
}
