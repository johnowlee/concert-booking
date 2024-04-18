package hhplus.concert.domain.concert.components;

import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConcertReaderTest {

    @Autowired
    private ConcertReaderRepository concertReaderRepository;

}