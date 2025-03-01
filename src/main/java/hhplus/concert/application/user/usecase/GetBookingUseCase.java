package hhplus.concert.application.user.usecase;

import hhplus.concert.core.booking.domain.service.BookingQueryService;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.domain.service.UserQueryService;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.BookingErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetBookingUseCase {

    private final UserQueryService userQueryService;
    private final BookingQueryService bookingQueryService;

    public Booking execute(Long userId, Long bookingId) {
        User user = userQueryService.getUserById(userId);
        Booking booking = bookingQueryService.getBookingById(bookingId);
        if (user.notEquals(booking.getUser())) {
            throw new RestApiException(BookingErrorCode.INVALID_BOOKER);
        }
        return booking;
    }
}
