package hhplus.concert.application.user.usecase;

import hhplus.concert.core.booking.domain.service.BookingQueryService;
import hhplus.concert.core.booking.domain.model.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetBookingsUseCase {

    private final BookingQueryService bookingQueryService;

    public List<Booking> execute(Long userId) {
        return bookingQueryService.getBookingsByUserId(userId);
    }
}