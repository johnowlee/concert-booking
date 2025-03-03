package hhplus.concert.application.concert;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetConcertOptionByIdUseCaseTest {

//    @Mock
//    ConcertOptionReader concertOptionReader;
//
//    @Mock
//    SeatReader seatReader;
//
//    @InjectMocks
//    GetConcertOptionByIdUseCase getConcertOptionByIdUseCase;
//
//    @DisplayName("콘서트 옵션 ID로 콘서트 옵션과 좌석목록을 가져온다.")
//    @Test
//    void execute() {
//        // given
//        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 15, 16, 00);
//        ConcertOption concertOption = ConcertOption.builder()
//                .concertDateTime(concertDateTime)
//                .place("stadium")
//                .build();
//
//        given(concertOptionReader.getConcertOptionById(1L)).willReturn(concertOption);
//
//        // when
//        ConcertOption result = getConcertOptionByIdUseCase.execute(1L);
//
//        // then
//        assertThat(result.getConcertDateTime()).isEqualTo(concertDateTime);
//        assertThat(result.getPlace()).isEqualTo("stadium");
//    }
}