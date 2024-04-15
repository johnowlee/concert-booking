package hhplus.concert.domain.concert.components;

import hhplus.concert.entities.concert.ConcertEntity;
import hhplus.concert.entities.concert.ConcertOptionEntity;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertReader {

    private final ConcertReaderRepository concertReaderRepository;

    public List<ConcertEntity> getConcerts() {
        return concertReaderRepository.getConcerts();
    }

    public List<ConcertOptionEntity> getConcertOptions() {
        return concertReaderRepository.getConcertOptions();
    }
}
