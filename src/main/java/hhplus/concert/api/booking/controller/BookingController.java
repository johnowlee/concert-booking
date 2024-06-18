package hhplus.concert.api.booking.controller;

import hhplus.concert.api.booking.dto.request.PaymentRequest;
import hhplus.concert.api.booking.dto.response.booking.BookingResponse;
import hhplus.concert.api.booking.dto.response.bookings.BookingsResponse;
import hhplus.concert.api.booking.dto.response.payment.PaymentResponse;
import hhplus.concert.api.booking.usecase.GetBookingByIdUseCase;
import hhplus.concert.api.booking.usecase.GetBookingsByUserIdUseCase;
import hhplus.concert.api.booking.usecase.PayBookingUseCase;
import hhplus.concert.api.queue.dto.request.QueueTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final GetBookingsByUserIdUseCase getBookingsByUserIdUseCase;
    private final GetBookingByIdUseCase getBookingByIdUseCase;
    private final PayBookingUseCase payBookingUseCase;

    @GetMapping("/users/{id}")
    public ResponseEntity<BookingsResponse> bookings(@PathVariable Long id) {
        return ResponseEntity.ok().body(getBookingsByUserIdUseCase.execute(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookingResponse> booking(@PathVariable Long id) {
        return ResponseEntity.ok().body(getBookingByIdUseCase.execute(id));
    }

    @PostMapping("{id}/payment")
    public ResponseEntity<PaymentResponse> payment(@PathVariable Long id,
                                   QueueTokenRequest queueTokenRequest,
                                   @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok().body(payBookingUseCase.execute(id, paymentRequest.userId()));
    }
}
