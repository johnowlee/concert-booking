package hhplus.concert.application.concert;

import hhplus.concert.application.concert.usecase.GetConcertsUseCase;
import hhplus.concert.core.concert.domain.service.ConcertQueryService;
import hhplus.concert.core.concert.domain.model.Concert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetConcertsUseCaseTest {

    @Mock
    ConcertQueryService concertQueryService;

    @InjectMocks
    GetConcertsUseCase getConcertsUseCase;

    @DisplayName("콘서트 목록을 조회한다.")
    @Test
    void testName() {
        // given
        Concert concert = Concert.builder()
                .title("concert")
                .organizer("jon")
                .build();
        given(concertQueryService.getConcerts()).willReturn(List.of(concert));

        // when
        List<Concert> result = getConcertsUseCase.execute();

        // then
        assertThat(result).hasSize(1)
                .extracting("title", "organizer")
                .contains(
                        tuple("concert", "jon")
                );
    }
}