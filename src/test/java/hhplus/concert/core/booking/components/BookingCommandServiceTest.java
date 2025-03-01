package hhplus.concert.core.booking.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.booking.domain.service.BookingCommandService;
import hhplus.concert.core.booking.infrastructure.repository.BookingJpaRepository;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import hhplus.concert.core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class BookingCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    BookingCommandService bookingCommandService;

    @Autowired
    BookingJpaRepository bookingJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @DisplayName("콘서트를 예약한다.")
    @Test
    void bookConcert() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        User savedUser = userJpaRepository.save(user);

        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 10, 11, 30, 30);
        Booking booking = Booking.builder()
                .bookingDateTime(bookingDateTime)
                .concertTitle("IU 콘서트")
                .user(savedUser)
                .build();

        // when
        bookingCommandService.saveBooking(booking);

        // then
        List<Booking> bookings = bookingJpaRepository.findAll();
        assertThat(bookings).hasSize(1)
                .extracting("user", "bookingDateTime", "concertTitle")
                .contains(tuple(savedUser, bookingDateTime, "IU 콘서트"));
    }
}