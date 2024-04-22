package hhplus.concert.api.booking.controller;

import hhplus.concert.api.booking.dto.response.booking.BookingResponse;
import hhplus.concert.api.booking.dto.response.bookings.BookingsResponse;
import hhplus.concert.api.booking.dto.response.payment.PaymentResponse;
import hhplus.concert.api.booking.usecase.GetBookingByIdUseCase;
import hhplus.concert.api.booking.usecase.GetBookingsByUserIdUseCase;
import hhplus.concert.api.booking.usecase.PayBookingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final GetBookingsByUserIdUseCase getBookingsByUserIdUseCase;
    private final GetBookingByIdUseCase getBookingByIdUseCase;
    private final PayBookingUseCase payBookingUseCase;

    @GetMapping("/users/{id}")
    public BookingsResponse bookings(@PathVariable Long id) {
        return getBookingsByUserIdUseCase.excute(id);
    }

    @GetMapping("{id}")
    public BookingResponse booking(@PathVariable Long id) {
        return getBookingByIdUseCase.excute(id);
    }

    @GetMapping("{id}/payment")
    public PaymentResponse booking(@PathVariable Long id,
                                   @RequestHeader("Queue-Token") String queueId) {
        return payBookingUseCase.excute(id, queueId);
    }
}
