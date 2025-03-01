package hhplus.concert.core.history.payment.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.booking.infrastructure.repository.BookingJpaRepository;
import hhplus.concert.core.booking.infrastructure.repository.BookingSeatJpaRepository;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.model.BookingSeat;
import hhplus.concert.core.booking.domain.model.BookingStatus;
import hhplus.concert.core.concert.infrastructure.repository.ConcertOptionJpaRepository;
import hhplus.concert.core.concert.infrastructure.repository.SeatJpaRepository;
import hhplus.concert.core.concert.domain.model.ConcertOption;
import hhplus.concert.core.concert.domain.model.Seat;
import hhplus.concert.core.concert.domain.model.SeatBookingStatus;
import hhplus.concert.core.concert.domain.model.SeatGrade;
import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.core.payment.infrastructure.adapter.PaymentCommandAdapter;
import hhplus.concert.core.payment.infrastructure.repository.PaymentJpaRepository;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import hhplus.concert.core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.core.concert.domain.model.SeatBookingStatus.BOOKED;
import static hhplus.concert.core.concert.domain.model.SeatBookingStatus.PROCESSING;
import static hhplus.concert.core.concert.domain.model.SeatGrade.A;
import static hhplus.concert.core.concert.domain.model.SeatGrade.B;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class PaymentCoreWriterRepositoryTest extends IntegrationTestSupport {

    @Autowired
    PaymentCommandAdapter paymentCoreWriterRepository;

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

        LocalDateTime paymentDateTime = LocalDateTime.of(2024, 8, 11, 17, 34);
        Payment payment = Payment.createPayment(savedBooking, paymentDateTime);

        // when
        paymentCoreWriterRepository.save(payment);

        // then
        List<Payment> paymentHistories = paymentJpaRepository.findAll();
        assertThat(paymentHistories).hasSize(1)
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