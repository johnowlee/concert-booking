package hhplus.concert.api.concert.usecase.response.concertOptions;

import hhplus.concert.api.common.response.ConcertOptionResponse;
import hhplus.concert.domain.concert.models.ConcertOption;

import java.util.List;
import java.util.stream.Collectors;

public record ConcertOptionsResponse(List<ConcertOptionResponse> concertOptions) {
    public static ConcertOptionsResponse from(List<ConcertOption> concertOptions) {
        return new ConcertOptionsResponse(
                concertOptions.stream()
                        .map(co -> ConcertOptionResponse.from(co))
                        .collect(Collectors.toList())
        );
    }
}
