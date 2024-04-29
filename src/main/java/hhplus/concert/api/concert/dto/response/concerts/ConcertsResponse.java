package hhplus.concert.api.concert.dto.response.concerts;

import hhplus.concert.domain.concert.models.Concert;

import java.util.List;
import java.util.stream.Collectors;

public record ConcertsResponse(List<ConcertDto> concerts) {

    public static ConcertsResponse from(List<Concert> concerts) {
        List<ConcertDto> collect = concerts.stream()
                .map(c -> ConcertDto.from(c))
                .collect(Collectors.toList());
        return new ConcertsResponse(collect);
    }
}
