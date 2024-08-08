package hhplus.concert.domain.history.payment.event;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.user.models.User;

public record PaymentCompleteEvent(long userId, long bookingId, long amount) {
    public static PaymentCompleteEvent of(User user, Booking booking) {
        return new PaymentCompleteEvent(user.getId(), booking.getId(), booking.getTotalPrice());
    }

    public static PaymentCompleteEvent from(Booking booking) {
        return new PaymentCompleteEvent(booking.getUser().getId(), booking.getId(), booking.getTotalPrice());
    }
}
