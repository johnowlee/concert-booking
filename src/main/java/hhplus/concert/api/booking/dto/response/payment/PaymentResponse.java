package hhplus.concert.api.booking.dto.response.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.user.models.User;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record PaymentResponse(
        Long id,
        LocalDateTime paymentDateTime,
        Integer paymentAmount,
        UserResponse user,
        BookingResponse booking
) {

    public static PaymentResponse from(Payment payment) {
        User user = payment.getUser();
        Booking booking = payment.getBooking();
        return new PaymentResponse(
                payment.getId(),
                payment.getPaymentDateTime(),
                payment.getPaymentAmount(),
                createUserResponse(user),
                createBookingResponse(booking)
        );
    }

    private static UserResponse createUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), null, null, null);
    }

    private static BookingResponse createBookingResponse(Booking booking) {
        return new BookingResponse(booking.getId(), booking.getBookingStatus(), null, booking.getConcertTitle(), null, null);
    }
}
