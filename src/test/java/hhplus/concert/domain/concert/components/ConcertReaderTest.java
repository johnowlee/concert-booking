package hhplus.concert.domain.concert.components;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConcertReaderTest {

    @InjectMocks
    private ConcertReader concertReader;

    @Mock
    ConcertReaderRepository concertReaderRepository;


    @DisplayName("인자값이 모두 유효하면, BalanceHistory 데이터 저장에 성공한다.")
    @Test
    void getConcerts_Success() {
        // given
        Concert concert1 = Concert.builder().id(1L).build();
        Concert concert2 = Concert.builder().id(2L).build();
        List<Concert> expected = List.of(concert1, concert2);
        given(concertReaderRepository.getConcerts()).willReturn(expected);

        // when
        List<Concert> result = concertReader.getConcerts();

        // then
        assertThat(result).isEqualTo(expected);
        assertThat(result.size()).isEqualTo(expected.size());
        verify(concertReaderRepository).getConcerts();
    }
}