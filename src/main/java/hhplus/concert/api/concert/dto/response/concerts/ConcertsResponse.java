package hhplus.concert.api.concert.dto.response.concerts;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;

import java.util.List;
import java.util.stream.Collectors;

public record ConcertsResponse(List<ConcertDto> concerts) {

    public static ConcertsResponse from(List<Concert> concerts) {
        List<ConcertDto> collect = concerts.stream()
                .map(c -> toConcertDto(c))
                .collect(Collectors.toList());
        return new ConcertsResponse(collect);
    }

    private static ConcertDto toConcertDto(Concert concert) {
        return ConcertDto.of(
                concert.getId(),
                concert.getTitle(),
                concert.getOrganizer(),
                createConcertOptionDtos(concert.getConcertOptions())
        );
    }

    private static List<ConcertOptionDto> createConcertOptionDtos(List<ConcertOption> options) {
        return options.stream()
                .map(o -> ConcertOptionDto.of(
                        o.getId(),
                        o.getPlace(),
                        o.getConcertDateTime(),
                        1)
                )
                .collect(Collectors.toList());
    }
}
