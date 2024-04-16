package hhplus.concert.domain.concert.components;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
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

    public Concert getConcertById(Long id) {
        return concertReaderRepository.getConcertById(id);
//        return concertReaderRepository.getConcerts();
    }

    public ConcertOption getConcertOptionById(Long id) {
        return concertReaderRepository.getConcertOptionById(id);
    }
}
