package hhplus.concert.core.concert.components;

import hhplus.concert.IntegrationTestSupport;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class ConcertOptionReaderTest extends IntegrationTestSupport {

//    @Autowired
//    ConcertOptionReader concertOptionReader;
//
//    @Autowired
//    ConcertOptionJpaRepository concertOptionJpaRepository;
//
//    @Autowired
//    ConcertJpaRepository concertJpaRepository;
//
//    @DisplayName("콘서트 옵션 id로 콘서트 옵션을 조회한다.")
//    @Test
//    void getConcertOptionById() {
//        // given
//        Concert concert = Concert.builder()
//                .title("IU concert")
//                .organizer("IU")
//                .build();
//        Concert savedConcert = concertJpaRepository.save(concert);
//
//        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
//
//        ConcertOption concertOption = ConcertOption.builder()
//                .concert(savedConcert)
//                .concertDateTime(concertDateTime)
//                .place("장충체육관")
//                .build();
//        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);
//
//        // when
//        ConcertOption result = concertOptionReader.getConcertOptionById(savedConcertOption.getId());
//
//        // then
//        assertThat(result).isNotNull();
//        assertThat(result.getConcert()).isEqualTo(savedConcertOption.getConcert());
//        assertThat(result.getConcertDateTime()).isEqualTo(savedConcertOption.getConcertDateTime());
//        assertThat(result.getPlace()).isEqualTo(savedConcertOption.getPlace());
//    }
//
//    @DisplayName("존재하지 않는 콘서트 옵션 id로 조회 시 예외가 발생한다.")
//    @Test
//    void getConcertOptionByIdWithNotExistedConcertOptionId() {
//        // given
//        Concert concert = Concert.builder()
//                .title("IU concert")
//                .organizer("IU")
//                .build();
//        Concert savedConcert = concertJpaRepository.save(concert);
//
//        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
//
//        ConcertOption concertOption = ConcertOption.builder()
//                .concert(savedConcert)
//                .concertDateTime(concertDateTime)
//                .place("장충체육관")
//                .build();
//        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);
//
//        // when & then
//        assertThat(savedConcertOption.getId()).isNotEqualTo(99L);
//        assertThatThrownBy(() -> concertOptionReader.getConcertOptionById(99L))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(CONCERT_OPTION_NOT_FOUND.getMessage());
//    }
//
//    @DisplayName("콘서트 id로 콘서트 옵션 목록을 조회한다.")
//    @Test
//    void getConcertOptionsByConcertId() {
//        // given
//        Concert concert = Concert.builder()
//                .title("IU concert")
//                .organizer("IU")
//                .build();
//        Concert savedConcert = concertJpaRepository.save(concert);
//
//        LocalDateTime concertDateTime1 = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
//
//        ConcertOption concertOption1 = ConcertOption.builder()
//                .concert(savedConcert)
//                .concertDateTime(concertDateTime1)
//                .place("장충체육관")
//                .build();
//
//        LocalDateTime concertDateTime2 = LocalDateTime.of(2024, 8, 10, 11, 30, 30);
//
//        ConcertOption concertOption2 = ConcertOption.builder()
//                .concert(savedConcert)
//                .concertDateTime(concertDateTime2)
//                .place("상암월드컵경기장")
//                .build();
//
//        concertOptionJpaRepository.saveAll(List.of(concertOption1, concertOption2));
//
//        // when
//        List<ConcertOption> result = concertOptionReader.getConcertOptionsByConcertId(savedConcert.getId());
//
//        // then
//        assertThat(result).hasSize(2)
//                .extracting("concert", "concertDateTime", "place")
//                .containsExactlyInAnyOrder(
//                        tuple(savedConcert, concertDateTime1, "장충체육관"),
//                        tuple(savedConcert, concertDateTime2, "상암월드컵경기장")
//                );
//    }
}