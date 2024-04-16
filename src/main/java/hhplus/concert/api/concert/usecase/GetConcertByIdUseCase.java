package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.response.ConcertResponse;
import hhplus.concert.domain.concert.components.ConcertReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetConcertByIdUseCase {

    private final ConcertReader concertReader;

    public ConcertResponse excute(Long id) {
        return new ConcertResponse(concertReader.getConcertById(id));
    }
}
