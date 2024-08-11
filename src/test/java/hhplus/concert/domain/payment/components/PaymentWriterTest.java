package hhplus.concert.domain.payment.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.booking.infrastructure.BookingJpaRepository;
import hhplus.concert.domain.booking.infrastructure.BookingSeatJpaRepository;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.models.BookingStatus;
import hhplus.concert.domain.concert.infrastructure.ConcertOptionJpaRepository;
import hhplus.concert.domain.concert.infrastructure.SeatJpaRepository;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.concert.models.SeatGrade;
import hhplus.concert.domain.history.payment.components.PaymentWriter;
import hhplus.concert.domain.history.payment.infrastructure.PaymentJpaRepository;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.user.infrastructure.UserJpaRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hhplus.concert.domain.concert.models.SeatBookingStatus.BOOKED;
import static hhplus.concert.domain.concert.models.SeatBookingStatus.PROCESSING;
import static hhplus.concert.domain.concert.models.SeatGrade.A;
import static hhplus.concert.domain.concert.models.SeatGrade.B;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class PaymentWriterTest extends IntegrationTestSupport {

    @Autowired
    PaymentWriter paymentWriter;

    @Autowired
    PaymentJpaRepository paymentJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    BookingJpaRepository bookingJpaRepository;

    @Autowired
    SeatJpaRepository seatJpaRepository;

    @Autowired
    BookingSeatJpaRepository bookingSeatJpaRepository;

    @Autowired
    ConcertOptionJpaRepository concertOptionJpaRepository;

    @DisplayName("결제내역을 저장한다.")
    @Test
    void save() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        User savedUser = userJpaRepository.save(user);

        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 11, 17, 30);
        Booking booking = Booking.builder()
                .concertTitle("IU 콘서트")
                .bookingDateTime(bookingDateTime)
                .user(savedUser)
                .bookingStatus(BookingStatus.COMPLETE)
                .bookingSeats(new ArrayList<>())
                .build();
        Booking savedBooking = bookingJpaRepository.save(booking);

        ConcertOption concertOption = ConcertOption.builder()
                .place("장충체육관")
                .build();
        ConcertOption savedConcertOption = concertOptionJpaRepository.save(concertOption);

        Seat seat1 = createSeat("A-1", BOOKED, savedConcertOption, A);
        Seat seat2 = createSeat("A-2", PROCESSING, savedConcertOption, B);
        seatJpaRepository.saveAll(List.of(seat1, seat2));

        BookingSeat bookingSeat1 = createBookingSeat(savedBooking, seat1);
        BookingSeat bookingSeat2 = createBookingSeat(savedBooking, seat2);
        bookingSeatJpaRepository.saveAll(List.of(bookingSeat1, bookingSeat2));

        // when
        LocalDateTime paymentDateTime = LocalDateTime.of(2024, 8, 11, 17, 34);
        paymentWriter.save(savedBooking, paymentDateTime);

        // then
        List<Payment> payments = paymentJpaRepository.findAll();
        assertThat(payments).hasSize(1)
                .extracting(
                        Payment::getUser,
                        Payment::getBooking,
                        Payment::getPaymentDateTime,
                        Payment::getPaymentAmount
                )
                .contains(
                        tuple(savedUser, savedBooking, paymentDateTime, savedBooking.getTotalPrice())
                );
    }

    private static BookingSeat createBookingSeat(Booking savedBooking, Seat seat) {
        return BookingSeat.builder()
                .booking(savedBooking)
                .seat(seat)
                .build();
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