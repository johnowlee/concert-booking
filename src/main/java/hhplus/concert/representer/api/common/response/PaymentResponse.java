package hhplus.concert.representer.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.user.models.User;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
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
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    private static BookingResponse createBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .bookingStatus(booking.getBookingStatus())
                .concertTitle(booking.getConcertTitle())
                .build();
    }
}
