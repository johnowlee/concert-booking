package hhplus.concert.domain.concert.components;

import hhplus.concert.entities.concert.ConcertEntity;
import hhplus.concert.entities.concert.ConcertOptionEntity;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ConcertReaderTest {

    @Autowired
    private ConcertReaderRepository concertReaderRepository;

    @Test
    void findAllConcertTest() {
        List<ConcertEntity> concerts = concertReaderRepository.getConcerts();
    }

    @Test
    void findAllConcertOptionsTest() {
        List<ConcertOptionEntity> concertOptions = concertReaderRepository.getConcertOptions();
    }
}