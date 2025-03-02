package hhplus.concert.application.user.data.command;

import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.payment.domain.model.Payment;

public record PaymentCompletionCommand(long payerId, long bookingId, long paymentAmount) {

    public static PaymentCompletionCommand from(Payment payment) {
        Booking booking = payment.getBooking();
        return new PaymentCompletionCommand(payment.getUser().getId(), booking.getId(), booking.getTotalPrice());
    }
}
