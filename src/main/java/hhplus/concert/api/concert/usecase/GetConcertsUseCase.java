package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.response.ConcertsResponse;
import hhplus.concert.domain.concert.components.ConcertReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.concert.dto.ConcertDtoMapper.createConcertDtos;

@Service
@RequiredArgsConstructor
public class GetConcertsUseCase {

    private final ConcertReader concertReader;

    public ConcertsResponse excute() {
        return ConcertsResponse.from(createConcertDtos(concertReader.getConcerts()));
    }
}
