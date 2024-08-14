package hhplus.concert.domain.concert.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.concert.infrastructure.ConcertJpaRepository;
import hhplus.concert.domain.concert.infrastructure.ConcertOptionJpaRepository;
import hhplus.concert.domain.concert.infrastructure.SeatJpaRepository;
import hhplus.concert.domain.concert.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.api.exception.code.ConcertErrorCode.CONCERT_OPTION_NOT_FOUND;
import static hhplus.concert.domain.concert.models.SeatBookingStatus.*;
import static hhplus.concert.domain.concert.models.SeatGrade.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class ConcertOptionReaderTest extends IntegrationTestSupport {

    @Autowired
    ConcertOptionReader concertOptionReader;

    @Autowired
    ConcertOptionJpaRepository concertOptionJpaRepository;

    @Autowired
    ConcertJpaRepository concertJpaRepository;

    @Autowired
    SeatJpaRepository seatJpaRepository;

    @DisplayName("콘서트 옵션 id로 콘서트 옵션을 조회한다.")
    @Test
    void getConcertOptionById() {
        // given
        Concert concert = Concert.builder()
                .title("IU concert")
                .organizer("IU")
                .build();
        Concert savedConcert = concertJpaRepository.save(concert);

        Seat seat1 = createSeat("A-1", BOOKED, A);
        Seat seat2 = createSeat("A-2", PROCESSING, B);
        Seat seat3 = createSeat("A-3", AVAILABLE, C);
        Seat seat4 = createSeat("A-4", BOOKED, C);
        List<Seat> seats = seatJpaRepository.saveAll(List.of(seat1, seat2, seat3, seat4));

        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);

        ConcertOption concertOption = ConcertOption.builder()
                .concert(savedConcert)
                .concertDateTime(concertDateTime)
                .place("장충체육관")
                .seats(seats)
                .build();
        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);

        // when
        ConcertOption result = concertOptionReader.getConcertOptionById(savedConcertOption.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getConcert()).isEqualTo(savedConcertOption.getConcert());
        assertThat(result.getConcertDateTime()).isEqualTo(savedConcertOption.getConcertDateTime());
        assertThat(result.getPlace()).isEqualTo(savedConcertOption.getPlace());
        assertThat(result.getSeats()).isEqualTo(savedConcertOption.getSeats());
    }

    @DisplayName("존재하지 않는 콘서트 옵션 id로 조회 시 예외가 발생한다.")
    @Test
    void getConcertOptionByIdWithNotExistedConcertOptionId() {
        // given
        Concert concert = Concert.builder()
                .title("IU concert")
                .organizer("IU")
                .build();
        Concert savedConcert = concertJpaRepository.save(concert);

        Seat seat1 = createSeat("A-1", BOOKED, A);
        Seat seat2 = createSeat("A-2", PROCESSING, B);
        Seat seat3 = createSeat("A-3", AVAILABLE, C);
        Seat seat4 = createSeat("A-4", BOOKED, C);
        List<Seat> seats = seatJpaRepository.saveAll(List.of(seat1, seat2, seat3, seat4));

        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);

        ConcertOption concertOption = ConcertOption.builder()
                .concert(savedConcert)
                .concertDateTime(concertDateTime)
                .place("장충체육관")
                .seats(seats)
                .build();
        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);

        // when & then
        assertThat(savedConcertOption.getId()).isNotEqualTo(2L);
        assertThatThrownBy(() -> concertOptionReader.getConcertOptionById(2L))
                .isInstanceOf(RestApiException.class)
                .hasMessage(CONCERT_OPTION_NOT_FOUND.getMessage());
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

        Seat seat1 = createSeat("A-1", BOOKED, A);
        Seat seat2 = createSeat("A-2", PROCESSING, B);
        Seat seat3 = createSeat("A-3", AVAILABLE, C);
        Seat seat4 = createSeat("A-4", BOOKED, C);
        List<Seat> savedSeats1 = seatJpaRepository.saveAll(List.of(seat1, seat2, seat3, seat4));

        LocalDateTime concertDateTime1 = LocalDateTime.of(2024, 8, 9, 11, 30, 30);

        ConcertOption concertOption1 = ConcertOption.builder()
                .concert(savedConcert)
                .concertDateTime(concertDateTime1)
                .place("장충체육관")
                .seats(savedSeats1)
                .build();

        Seat seat5 = createSeat("B-1", AVAILABLE, B);
        Seat seat6 = createSeat("B-2", BOOKED, B);
        Seat seat7 = createSeat("B-3", AVAILABLE, C);
        Seat seat8 = createSeat("B-4", PROCESSING, A);
        List<Seat> savedSeats2 = seatJpaRepository.saveAll(List.of(seat5, seat6, seat7, seat8));

        LocalDateTime concertDateTime2 = LocalDateTime.of(2024, 8, 10, 11, 30, 30);

        ConcertOption concertOption2 = ConcertOption.builder()
                .concert(savedConcert)
                .concertDateTime(concertDateTime2)
                .place("상암월드컵경기장")
                .seats(savedSeats2)
                .build();

        List<ConcertOption> savedConcertOptions = concertOptionJpaRepository.saveAll(List.of(concertOption1, concertOption2));

        // when
        List<ConcertOption> result = concertOptionReader.getConcertOptionsByConcertId(savedConcert.getId());

        // then
        assertThat(result).hasSize(2)
                .extracting("concert", "concertDateTime", "place", "seats")
                .containsExactlyInAnyOrder(
                        tuple(savedConcert, concertDateTime1, "장충체육관", concertOption1.getSeats()),
                        tuple(savedConcert, concertDateTime2, "상암월드컵경기장", concertOption2.getSeats())
                );
    }

    private static Seat createSeat(String seatNo, SeatBookingStatus seatBookingStatus, SeatGrade grade) {
        return Seat.builder()
                .seatNo(seatNo)
                .seatBookingStatus(seatBookingStatus)
                .grade(grade)
                .build();
    }
}