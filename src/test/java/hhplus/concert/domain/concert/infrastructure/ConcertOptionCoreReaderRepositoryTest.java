package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.concert.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class ConcertOptionCoreReaderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    ConcertOptionCoreReaderRepository concertOptionCoreReaderRepository;

    @Autowired
    ConcertOptionJpaRepository concertOptionJpaRepository;

    @Autowired
    ConcertJpaRepository concertJpaRepository;

    @DisplayName("콘서트 옵션 id로 콘서트 옵션을 조회한다.")
    @Test
    void getConcertOptionById() {
        // given
        Concert concert = Concert.builder()
                .title("IU concert")
                .organizer("IU")
                .build();
        Concert savedConcert = concertJpaRepository.save(concert);

        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);

        ConcertOption concertOption = ConcertOption.builder()
                .concert(savedConcert)
                .concertDateTime(concertDateTime)
                .place("장충체육관")
                .build();
        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);

        // when
        Optional<ConcertOption> result = concertOptionCoreReaderRepository.getConcertOptionById(savedConcertOption.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getConcert()).isEqualTo(savedConcertOption.getConcert());
        assertThat(result.get().getConcertDateTime()).isEqualTo(savedConcertOption.getConcertDateTime());
        assertThat(result.get().getPlace()).isEqualTo(savedConcertOption.getPlace());
    }

    @DisplayName("존재하지 않는 콘서트 옵션 id로 조회 시 null이 조회된다.")
    @Test
    void getConcertOptionByIdWithNotExistedConcertOptionId() {
        // given
        Concert concert = Concert.builder()
                .title("IU concert")
                .organizer("IU")
                .build();
        Concert savedConcert = concertJpaRepository.save(concert);

        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);

        ConcertOption concertOption = ConcertOption.builder()
                .concert(savedConcert)
                .concertDateTime(concertDateTime)
                .place("장충체육관")
                .build();
        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);

        // when
        Optional<ConcertOption> result = concertOptionCoreReaderRepository.getConcertOptionById(2L);

        // then
        assertThat(savedConcertOption.getId()).isNotEqualTo(2L);
        assertThat(result).isEmpty();
    }

    @DisplayName("콘서트 id로 콘서트 옵션 목록을 조회한다.")
    @Test
    void getConcertOptionsByConcertId() {
        // given
        Concert concert = Concert.builder()
                .title("IU concert")
                .organizer("IU")
                .build();
        Concert savedConcert = concertJpaRepository.save(concert);

        LocalDateTime concertDateTime1 = LocalDateTime.of(2024, 8, 9, 11, 30, 30);

        ConcertOption concertOption1 = ConcertOption.builder()
                .concert(savedConcert)
                .concertDateTime(concertDateTime1)
                .place("장충체육관")
                .build();

        LocalDateTime concertDateTime2 = LocalDateTime.of(2024, 8, 10, 11, 30, 30);

        ConcertOption concertOption2 = ConcertOption.builder()
                .concert(savedConcert)
                .concertDateTime(concertDateTime2)
                .place("상암월드컵경기장")
                .build();

        concertOptionJpaRepository.saveAll(List.of(concertOption1, concertOption2));

        // when
        List<ConcertOption> result = concertOptionCoreReaderRepository.getConcertOptionsByConcertId(savedConcert.getId());

        // then
        assertThat(result).hasSize(2)
                .extracting("concert", "concertDateTime", "place")
                .containsExactlyInAnyOrder(
                        tuple(savedConcert, concertDateTime1, "장충체육관"),
                        tuple(savedConcert, concertDateTime2, "상암월드컵경기장")
                );
    }
}