package hhplus.concert.domain.concert.components;

import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.repositories.SeatReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SeatReaderTest {

    @InjectMocks
    SeatReader seatReader;

    @Mock
    SeatReaderRepository seatReaderRepository;

    @Test
    void getSeatsByIds_Success() {
        // given
        List<Seat> expected = List.of(Seat.builder().build());
        given(seatReaderRepository.getSeatsByIds(any(List.class))).willReturn(expected);

        // when
        List<Seat> result = seatReader.getSeatsByIds(new ArrayList<>());

        // then
        assertEquals(expected, result);
        then(seatReaderRepository).should(times(1)).getSeatsByIds(any(List.class));
    }

}