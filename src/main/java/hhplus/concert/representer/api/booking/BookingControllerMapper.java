package hhplus.concert.representer.api.booking;

import hhplus.concert.application.user.data.command.ConcertBookingCommand;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.representer.api.booking.request.ConcertBookingRequest;
import hhplus.concert.representer.api.booking.response.BookingResponse;
import hhplus.concert.representer.api.booking.response.PaymentResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingControllerMapper {

    public BookingResponse toBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getBookingStatus(),
                booking.getBookingDateTime(),
                booking.getConcertTitle()
        );
    }

    public List<BookingResponse> toBookingResponseList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toBookingResponse)
                .collect(Collectors.toList());
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getPaymentDateTime(), payment.getPaymentAmount());
    }

    public ConcertBookingCommand toConcertBookingDto(ConcertBookingRequest request) {
        return new ConcertBookingCommand(request.seatIds());
    }
}
