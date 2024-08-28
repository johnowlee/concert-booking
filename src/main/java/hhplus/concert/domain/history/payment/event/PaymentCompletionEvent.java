package hhplus.concert.domain.history.payment.event;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;

public record PaymentCompletionEvent(long userId, long bookingId, long amount) {

    public static PaymentCompletionEvent from(Payment payment) {
        Booking booking = payment.getBooking();
        return new PaymentCompletionEvent(booking.getUser().getId(), booking.getId(), booking.getTotalPrice());
    }
}
