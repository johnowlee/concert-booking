package hhplus.concert.domain.concert.components;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import hhplus.concert.entities.concert.ConcertOptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertReader {

    private final ConcertReaderRepository concertReaderRepository;

    public List<Concert> getConcerts() {
        return concertReaderRepository.getConcerts();
    }

    public List<ConcertOptionEntity> getConcertOptions() {
        return concertReaderRepository.getConcertOptions();
    }
}
