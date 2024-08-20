package hhplus.concert.api.concert.usecase.response;

import hhplus.concert.api.common.response.ConcertResponse;
import hhplus.concert.domain.concert.models.Concert;

import java.util.List;
import java.util.stream.Collectors;

public record ConcertsResponse(List<ConcertResponse> concerts) {

    public static ConcertsResponse from(List<Concert> concerts) {
        List<ConcertResponse> collect = concerts.stream()
                .map(c -> ConcertResponse.from(c))
                .collect(Collectors.toList());
        return new ConcertsResponse(collect);
    }
}
