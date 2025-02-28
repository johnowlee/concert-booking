package hhplus.concert.representer.api.user;

import hhplus.concert.representer.api.user.request.BalanceChargeRequest;
import hhplus.concert.representer.api.user.request.ConcertBookingRequest;
import hhplus.concert.representer.api.user.response.BalanceResponse;
import hhplus.concert.representer.api.user.response.BookingResponse;
import hhplus.concert.representer.api.user.response.PaymentResponse;
import hhplus.concert.application.user.dto.BalanceChargeDto;
import hhplus.concert.application.user.dto.ConcertBookingDto;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.user.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserControllerMapper {

    public BalanceResponse toBalanceResponse(User user) {
        return new BalanceResponse(user.getBalance());
    }

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

    public BalanceChargeDto toBalanceBalanceChargeRequest(BalanceChargeRequest request) {
        return new BalanceChargeDto(request.balance());
    }

    public ConcertBookingDto toConcertBookingDto(ConcertBookingRequest request) {
        return new ConcertBookingDto(request.seatIds());
    }
}
