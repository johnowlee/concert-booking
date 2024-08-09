package hhplus.concert.domain.concert.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.concert.infrastructure.ConcertJpaRepository;
import hhplus.concert.domain.concert.models.Concert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class ConcertReaderTest extends IntegrationTestSupport {

    @Autowired
    ConcertReader concertReader;

    @Autowired
    ConcertJpaRepository concertJpaRepository;

    @DisplayName("콘서트 목록을 조회한다.")
    @Test
    void getConcerts() {
        // given
        Concert concert1 = Concert.builder()
                .title("IU concert")
                .organizer("IU")
                .build();
        Concert concert2 = Concert.builder()
                .title("NewJeans concert")
                .organizer("NewJeans")
                .build();
        Concert concert3 = Concert.builder()
                .title("AESPA concert")
                .organizer("AESPA")
                .build();
        concertJpaRepository.saveAll(List.of(concert1, concert2, concert3));

        // when
        List<Concert> concerts = concertReader.getConcerts();

        // then
        assertThat(concerts).hasSize(3)
                .extracting("title", "organizer")
                .containsExactlyInAnyOrder(
                        tuple("IU concert", "IU"),
                        tuple("NewJeans concert", "NewJeans"),
                        tuple("AESPA concert", "AESPA")
                );
    }

}