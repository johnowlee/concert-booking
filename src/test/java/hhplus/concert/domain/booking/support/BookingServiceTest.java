package hhplus.concert.domain.booking.support;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.booking.components.BookingSeatReader;
import hhplus.concert.domain.booking.infrastructure.BookingJpaRepository;
import hhplus.concert.domain.booking.infrastructure.BookingSeatJpaRepository;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.infrastructure.ConcertJpaRepository;
import hhplus.concert.domain.concert.infrastructure.ConcertOptionJpaRepository;
import hhplus.concert.domain.concert.infrastructure.SeatJpaRepository;
import hhplus.concert.domain.concert.models.*;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.infrastructure.UserJpaRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.domain.concert.models.SeatBookingStatus.*;
import static hhplus.concert.domain.concert.models.SeatGrade.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Transactional
class BookingServiceTest extends IntegrationTestSupport {

    @Autowired
    BookingService bookingService;

    @MockBean
    ClockManager clockManager;

    @MockBean
    BookingSeatReader bookingSeatReader;

    @SpyBean
    BookingSeatManager bookingSeatManager;

    @Autowired
    ConcertJpaRepository concertJpaRepository;

    @Autowired
    SeatJpaRepository seatJpaRepository;

    @Autowired
    ConcertOptionJpaRepository concertOptionJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    BookingJpaRepository bookingJpaRepository;

    @Autowired
    BookingSeatJpaRepository bookingSeatJpaRepository;

    @DisplayName("콘서트를 예약한다.")
    @Test
    void book() {
        // given
        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 8, 15, 25);
        given(clockManager.getNowDateTime()).willReturn(bookingDateTime);

        Concert concert = Concert.builder()
                .title("IU 콘서트")
                .build();
        Concert savedConcert = concertJpaRepository.save(concert);

        ConcertOption concertOption = ConcertOption.builder()
                .concert(savedConcert)
                .build();
        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);

        Seat seat1 = createSeat("A-1", BOOKED, savedConcertOption, A);
        Seat seat2 = createSeat("A-2", PROCESSING, savedConcertOption, B);
        Seat seat3 = createSeat("A-3", AVAILABLE, savedConcertOption, C);
        Seat seat4 = createSeat("A-4", BOOKED, savedConcertOption, C);
        List<Seat> savedSeats = seatJpaRepository.saveAll(List.of(seat1, seat2, seat3, seat4));

        User user = User.builder()
                .name("jon")
                .build();
        User savedUser = userJpaRepository.save(user);

        // when
        Booking result = bookingService.book(savedUser, savedSeats);

        // then
        assertThat(result)
                .extracting(
                        Booking::getConcertTitle,
                        Booking::getBookingDateTime,
                        Booking::getUser
                )
                .contains("IU 콘서트", bookingDateTime, savedUser);

        List<BookingSeat> bookingSeats = bookingSeatJpaRepository.findAll();
        assertThat(bookingSeats).hasSize(4)
                .extracting(
                        BookingSeat::getBooking,
                        BookingSeat::getSeat
                )
                .containsExactlyInAnyOrder(
                        tuple(result, seat1),
                        tuple(result, seat2),
                        tuple(result, seat3),
                        tuple(result, seat4)
                );

        List<Seat> seats = seatJpaRepository.findAll();
        assertThat(seats).hasSize(4)
                .extracting(
                        Seat::getSeatNo,
                        Seat::getSeatBookingStatus,
                        Seat::getConcertOption,
                        Seat::getGrade
                )
                .containsExactlyInAnyOrder(
                        tuple("A-1", PROCESSING, savedConcertOption, A),
                        tuple("A-2", PROCESSING, savedConcertOption, B),
                        tuple("A-3", PROCESSING, savedConcertOption, C),
                        tuple("A-4", PROCESSING, savedConcertOption, C)
                );
    }

    @DisplayName("예약 가능 여부를 검증한다.")
    @Test
    void validateBookability() {
        // given
        List<BookingSeat> bookingSeats = List.of(mock(BookingSeat.class));
        LocalDateTime dateTime = mock(LocalDateTime.class);

        given(bookingSeatReader.getBookingSeatsBySeatIds(anyList())).willReturn(bookingSeats);
        given(clockManager.getNowDateTime()).willReturn(dateTime);
        willDoNothing().given(bookingSeatManager).validateBookable(bookingSeats, dateTime);

        // when
        bookingService.validateBookability(anyList());

        // then
        verify(bookingSeatReader).getBookingSeatsBySeatIds(anyList());
        verify(clockManager).getNowDateTime();
        verify(bookingSeatManager).validateBookable(bookingSeats, dateTime);
    }

    private static Seat createSeat(String seatNo, SeatBookingStatus booked, ConcertOption savedConcertOption, SeatGrade a) {
        return Seat.builder()
                .seatNo(seatNo)
                .seatBookingStatus(booked)
                .concertOption(savedConcertOption)
                .grade(a)
                .build();
    }
}