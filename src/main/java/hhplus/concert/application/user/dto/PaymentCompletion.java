package hhplus.concert.application.user.dto;

import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.payment.domain.model.Payment;

public record PaymentCompletion(long payerId, long bookingId, long paymentAmount) {

    public static PaymentCompletion from(Payment payment) {
        Booking booking = payment.getBooking();
        return new PaymentCompletion(payment.getUser().getId(), booking.getId(), booking.getTotalPrice());
    }
}
