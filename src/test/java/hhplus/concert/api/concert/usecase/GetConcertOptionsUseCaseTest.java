package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.usecase.response.concertOptions.ConcertOptionsResponse;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetConcertOptionsUseCaseTest {

    @Mock
    ConcertOptionReader concertOptionReader;

    @InjectMocks
    GetConcertOptionsUseCase getConcertOptionsUseCase;

    @DisplayName("콘서트 ID로 콘서트 옵션 목록을 가져온다.")
    @Test
    void execute() {
        // given
        LocalDateTime concertDateTime = LocalDateTime.of(2024, 8, 15, 16, 00);
        ConcertOption concertOption = ConcertOption.builder()
                .concertDateTime(concertDateTime)
                .place("stadium")
                .build();

        given(concertOptionReader.getConcertOptionsByConcertId(1L)).willReturn(List.of(concertOption));

        // when
        ConcertOptionsResponse result = getConcertOptionsUseCase.execute(1L);

        // then
        assertThat(result.concertOptions()).hasSize(1)
                .extracting("place", "dateTime")
                .contains(
                        tuple("stadium", concertDateTime)
                );
    }
}