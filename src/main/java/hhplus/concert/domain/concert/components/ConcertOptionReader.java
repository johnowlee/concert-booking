package hhplus.concert.domain.concert.components;

import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.repositories.ConcertOptionReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertOptionReader {

    private final ConcertOptionReaderRepository concertOptionRepository;

    public ConcertOption getConcertOptionById(Long id) {
        return concertOptionRepository.getConcertOptionById(id);
    }
}
