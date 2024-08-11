package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.models.BookingStatus;
import hhplus.concert.domain.concert.infrastructure.SeatJpaRepository;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class BookingSeatCoreReaderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    BookingSeatCoreReaderRepository bookingSeatCoreReaderRepository;

    @Autowired
    BookingSeatJpaRepository bookingSeatJpaRepository;

    @Autowired
    SeatJpaRepository seatJpaRepository;

    @Autowired
    BookingJpaRepository bookingJpaRepository;

    @DisplayName("좌석Id 리스트로 BookingSeat 목록을 조회한다.")
    @Test
    void getBookingSeatsBySeatIds() {
        // given
        Seat seat1 = Seat.builder()
                .seatNo("A-1")
                .seatBookingStatus(SeatBookingStatus.BOOKED)
                .build();
        Seat seat2 = Seat.builder()
                .seatNo("A-2")
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();
        seatJpaRepository.saveAll(List.of(seat1, seat2));

        Booking booking = Booking.builder()
                .concertTitle("IU 콘서트")
                .bookingStatus(BookingStatus.INCOMPLETE)
                .build();
       bookingJpaRepository.save(booking);

        BookingSeat bookingSeat1 = BookingSeat.builder()
                .seat(seat1)
                .booking(booking)
                .build();
        BookingSeat bookingSeat2 = BookingSeat.builder()
                .seat(seat2)
                .booking(booking)
                .build();
        bookingSeatJpaRepository.saveAll(List.of(bookingSeat1, bookingSeat2));

        // when
        List<BookingSeat> result = bookingSeatCoreReaderRepository.getBookingSeatsBySeatIds(List.of(seat1.getId(), seat2.getId()));

        // then
        assertThat(result).hasSize(2)
                .extracting(
                        BookingSeat::getBooking,
                        BookingSeat::getSeat
                )
                .containsExactlyInAnyOrder(
                        tuple(booking, seat1),
                        tuple(booking, seat2)
                );
    }

}