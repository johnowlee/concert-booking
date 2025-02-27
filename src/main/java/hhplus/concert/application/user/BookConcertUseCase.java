package hhplus.concert.application.user;

import hhplus.concert.application.user.dto.ConcertBookingDto;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.service.BookingService;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookConcertUseCase {

    private final SeatReader seatReader;
    private final UserReader userReader;
    private final BookingService bookingService;

    @Transactional
    public Booking execute(Long userId, ConcertBookingDto dto) {
        bookingService.validateBookability(dto.seatIds());

        User user = userReader.getUserById(userId);
        List<Seat> seats = seatReader.getSeatsByIds(dto.seatIds());

        return bookingService.book(user, seats);
    }
}
