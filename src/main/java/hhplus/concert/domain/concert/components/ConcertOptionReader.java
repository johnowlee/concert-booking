package hhplus.concert.domain.concert.components;

import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.repositories.ConcertOptionReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertOptionReader {

    private final ConcertOptionReaderRepository concertOptionRepository;

    public ConcertOption getConcertOptionById(Long id) {
        return concertOptionRepository.getConcertOptionById(id);
    }

    public List<ConcertOption> getConcertOptionsByConcertId(Long concertId) {
        return concertOptionRepository.getConcertOptionsByConcertId(concertId);
    }
}
