package hhplus.concert.domain.booking.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingStatus;
import hhplus.concert.domain.booking.repositories.BookingReaderRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BookingReaderTest {

    BookingReader bookingReader;

    BookingReaderRepository bookingReaderRepository;

    @BeforeEach
    void setUp() {
        bookingReaderRepository = Mockito.mock(BookingReaderRepository.class);
        bookingReader = new BookingReader(bookingReaderRepository);
    }


    @DisplayName("인자값이 유효하면, Booking 리스트 조회에 성공한다.")
    @Test
    void getBookingsByUserId_Success_ifWithValidArguments() {
        // given
        Booking booking1 = Booking.builder()
                .user(User.builder().id(1L).build())
                .build();
        Booking booking2 = Booking.builder()
                .user(User.builder().id(1L).build())
                .build();
        Booking booking3 = Booking.builder()
                .user(User.builder().id(2L).build())
                .build();
        List<Booking> expectedBookings = List.of(booking1, booking2);

        given(bookingReaderRepository.getBookingsByUserId(1L)).willReturn(expectedBookings);

        // when
        List<Booking> result = bookingReader.getBookingsByUserId(1L);

        // then
        assertThat(result).isEqualTo(expectedBookings);
        assertThat(result.size()).isEqualTo(2);
        verify(bookingReaderRepository, times(1)).getBookingsByUserId(1L);
    }

    @DisplayName("인자값이 유효하면, Booking 조회에 성공한다.")
    @Test
    void getBookingById_Success_ifWithValidArguments() {
        // given
        Booking expectedBooking = Booking.builder()
                .id(1L)
                .concertTitle("콘서트A")
                .bookingStatus(BookingStatus.COMPLETE)
                .user(User.builder().id(2L).build())
                .build();

        given(bookingReaderRepository.getBookingById(1L)).willReturn(Optional.ofNullable(expectedBooking));

        // when
        Booking result = bookingReader.getBookingById(1L);

        // then
        assertThat(result).isEqualTo(expectedBooking);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getConcertTitle()).isEqualTo("콘서트A");
        assertThat(result.getUser().getId()).isEqualTo(2L);
        verify(bookingReaderRepository, times(1)).getBookingById(1L);
    }

    //TODO: 실패케이스 작성
}