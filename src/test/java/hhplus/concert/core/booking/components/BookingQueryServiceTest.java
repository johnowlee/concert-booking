package hhplus.concert.core.booking.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.booking.domain.service.BookingQueryService;
import hhplus.concert.representer.exception.RestApiException;
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

import static hhplus.concert.representer.exception.code.BookingErrorCode.NOT_FOUND_BOOKING;
import static org.assertj.core.api.Assertions.*;

@Transactional
class BookingQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    BookingQueryService bookingQueryService;

    @Autowired
    BookingJpaRepository bookingJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @DisplayName("예약한 유저 ID로 예약 목록을 조회한다.")
    @Test
    void getBookingsByUserId() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        User savedUser = userJpaRepository.save(user);

        LocalDateTime bookingDateTime1 = LocalDateTime.of(2024, 8, 10, 11, 30, 30);
        Booking booking1 = Booking.builder()
                .bookingDateTime(bookingDateTime1)
                .concertTitle("IU 콘서트")
                .user(savedUser)
                .build();

        LocalDateTime bookingDateTime2 = LocalDateTime.of(2024, 8, 13, 21, 30, 30);
        Booking booking2 = Booking.builder()
                .bookingDateTime(bookingDateTime2)
                .concertTitle("NewJeans 콘서트")
                .user(savedUser)
                .build();
        bookingJpaRepository.saveAll(List.of(booking1, booking2));

        // when
        List<Booking> result = bookingQueryService.findBookingsByUserId(savedUser.getId());

        // then
        assertThat(result).hasSize(2)
                .extracting("user", "bookingDateTime", "concertTitle")
                .containsExactlyInAnyOrder(
                        tuple(savedUser, bookingDateTime1, "IU 콘서트"),
                        tuple(savedUser, bookingDateTime2, "NewJeans 콘서트")
                );
    }

    @DisplayName("예약 ID로 예약을 단건 조회한다.")
    @Test
    void getBookingById() {
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
        Booking savedBooking = bookingJpaRepository.save(booking);

        // when
        Booking result = bookingQueryService.getBookingById(savedBooking.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUser()).isEqualTo(savedUser);
        assertThat(result.getBookingDateTime()).isEqualTo(bookingDateTime);
        assertThat(result.getConcertTitle()).isEqualTo("IU 콘서트");
    }

    @DisplayName("존재하지 않는 예약 ID로 조회 시 예외가 발생한다.")
    @Test
    void getBookingByIdWithNotExistedBookingId() {
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
        Booking savedBooking = bookingJpaRepository.save(booking);

        // when & then
        assertThatThrownBy(() -> bookingQueryService.getBookingById(2L))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_BOOKING.getMessage());
    }

}