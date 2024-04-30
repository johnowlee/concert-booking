package hhplus.concert.domain.booking.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.repositories.BookingWriterRepository;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookingWriterTest {

    @InjectMocks
    BookingWriter bookingWriter;

    @Mock
    BookingWriterRepository bookingWriterRepository;

    @BeforeEach
    void setUp() {
        bookingWriterRepository = Mockito.mock(BookingWriterRepository.class);
        bookingWriter = new BookingWriter(bookingWriterRepository);
    }

    @DisplayName("인자값이 모두 유효하면, Booking 데이터 저장에 성공한다.")
    @Test
    void bookConcert_Success_ifWithValidArguments() {
        // given
        Concert concert = Concert.builder().build();
        User user = User.builder().build();
        ConcertOption concertOption = ConcertOption.builder().concert(concert).build();
        Booking expectedBooking = Booking.buildBooking(concertOption, user);

        given(bookingWriterRepository.bookConcert(any(Booking.class))).willReturn(expectedBooking);

        // when
        Booking result = bookingWriter.bookConcert(Booking.buildBooking(concertOption, user));

        // then
        assertThat(result).isEqualTo(expectedBooking);
        verify(bookingWriterRepository, times(1)).bookConcert(any(Booking.class));
    }

    @DisplayName("인자값이 모두 유효하면, BookingSeat 데이터 저장에 성공한다.")
    @Test
    void saveAllBookingSeat_Success_ifWithValidArguments() {
        // given
        BookingSeat bookingSeat1 = BookingSeat.builder()
                .id(1L)
                .seat(Seat.builder().seatNo("A").build())
                .booking(Booking.builder().id(1L).build())
                .build();
        BookingSeat bookingSeat2 = BookingSeat.builder()
                .id(2L)
                .seat(Seat.builder().seatNo("B").build())
                .booking(Booking.builder().id(1L).build())
                .build();

        List<BookingSeat> expectedBooking = List.of(bookingSeat1, bookingSeat2);

        given(bookingWriterRepository.saveAllBookingSeat(any(List.class))).willReturn(expectedBooking);

        // when
        List<BookingSeat> result = bookingWriter.saveAllBookingSeat(List.of(bookingSeat1, bookingSeat2));

        // then
        verify(bookingWriterRepository, times(1)).saveAllBookingSeat(any(List.class));
        assertThat(result.size()).isEqualTo(2);
    }
    //TODO: 실패케이스 작성

}