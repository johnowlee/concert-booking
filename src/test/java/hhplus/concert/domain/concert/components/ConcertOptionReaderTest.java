package hhplus.concert.domain.concert.components;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.ConcertErrorCode;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.repositories.ConcertOptionReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ConcertOptionReaderTest {

    @InjectMocks
    ConcertOptionReader concertOptionReader;

    @Mock
    ConcertOptionReaderRepository concertOptionReaderRepository;

    @Test
    void getConcertOptionById_ReturnConcertOption_ifPresent() {
        // given
        Optional<ConcertOption> expected = Optional.ofNullable(ConcertOption.builder().build());
        given(concertOptionReaderRepository.getConcertOptionById(anyLong())).willReturn(expected);

        // when
        Optional<ConcertOption> result = Optional.ofNullable(concertOptionReader.getConcertOptionById(anyLong()));

        // then
        assertEquals(expected, result);
        then(concertOptionReaderRepository).should(times(1)).getConcertOptionById(anyLong());
    }

    @Test
    void getConcertOptionById_WillThrowException_ifNotPresent() {
        // given
        given(concertOptionReaderRepository.getConcertOptionById(anyLong())).willReturn(Optional.empty());

        // when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            concertOptionReader.getConcertOptionById(anyLong());
        });

        // then
        assertEquals(ConcertErrorCode.CONCERT_OPTION_NOT_FOUND, exception.getErrorCode());
        then(concertOptionReaderRepository).should(times(1)).getConcertOptionById(anyLong());
    }

    @Test
    void getConcertOptionsByConcertId_ReturnConcertOptionList_ifPresent() {
        // given
        List<ConcertOption> expected = new ArrayList<>();
        given(concertOptionReaderRepository.getConcertOptionsByConcertId(anyLong())).willReturn(expected);

        // when
        List<ConcertOption> result = concertOptionReader.getConcertOptionsByConcertId(anyLong());

        // then
        assertEquals(expected, result);
        then(concertOptionReaderRepository).should(times(1)).getConcertOptionsByConcertId(anyLong());
    }
}