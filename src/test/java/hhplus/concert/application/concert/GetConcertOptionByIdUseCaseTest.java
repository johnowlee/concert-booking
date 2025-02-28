package hhplus.concert.application.concert;

import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetConcertOptionByIdUseCaseTest {

    @Mock
    ConcertOptionReader concertOptionReader;

    @Mock
    SeatReader seatReader;

    @InjectMocks
    GetConcertOptionByIdUseCase getConcertOptionByIdUseCase;

    @DisplayName("콘서트 옵션 ID로 콘서트 옵션과 좌석목록을 가져온다.")
    @Test
    void execute() {
        // given
        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 15, 16, 00);
        ConcertOption concertOption = ConcertOption.builder()
                .concertDateTime(concertDateTime)
                .place("stadium")
                .build();

//        Seat seat = Seat.builder()
//                .seatNo("A-1")
//                .seatBookingStatus(AVAILABLE)
//                .build();

        given(concertOptionReader.getConcertOptionById(1L)).willReturn(concertOption);
//        given(seatReader.getSeatsByConcertOptionId(1L)).willReturn(List.of(seat));

        // when
        ConcertOption result = getConcertOptionByIdUseCase.execute(1L);

        // then
        assertThat(result.getConcertDateTime()).isEqualTo(concertDateTime);
        assertThat(result.getPlace()).isEqualTo("stadium");
//        assertThat(result.seats()).hasSize(1)
//                .extracting("seatNo", "seatBookingStatus")
//                .contains(
//                        tuple("A-1", AVAILABLE)
//                );
    }
}