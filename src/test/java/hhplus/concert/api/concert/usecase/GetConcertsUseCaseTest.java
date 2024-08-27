package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.usecase.response.ConcertsResponse;
import hhplus.concert.domain.concert.components.ConcertReader;
import hhplus.concert.domain.concert.models.Concert;
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
    ConcertReader concertReader;

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
        given(concertReader.getConcerts()).willReturn(List.of(concert));

        // when
        ConcertsResponse result = getConcertsUseCase.execute();

        // then
        assertThat(result.concerts()).hasSize(1)
                .extracting("title", "organizer")
                .contains(
                        tuple("concert", "jon")
                );
    }
}