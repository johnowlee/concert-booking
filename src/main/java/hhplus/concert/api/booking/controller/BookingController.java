package hhplus.concert.api.booking.controller;

import hhplus.concert.api.booking.controller.request.PaymentRequest;
import hhplus.concert.api.booking.usecase.GetBookingByIdUseCase;
import hhplus.concert.api.booking.usecase.GetBookingsByUserIdUseCase;
import hhplus.concert.api.booking.usecase.PayBookingUseCase;
import hhplus.concert.api.booking.usecase.response.BookingsResponse;
import hhplus.concert.api.common.RestApiResponse;
import hhplus.concert.api.common.response.BookingResponse;
import hhplus.concert.api.common.response.PaymentResponse;
import hhplus.concert.api.queue.controller.request.QueueTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final GetBookingsByUserIdUseCase getBookingsByUserIdUseCase;
    private final GetBookingByIdUseCase getBookingByIdUseCase;
    private final PayBookingUseCase payBookingUseCase;

    @GetMapping("/users/{userId}")
    public RestApiResponse<BookingsResponse> bookings(@PathVariable Long userId) {
        return RestApiResponse.ok(getBookingsByUserIdUseCase.execute(userId));
    }

    @GetMapping("{id}")
    public RestApiResponse<BookingResponse> booking(@PathVariable Long id) {
        return RestApiResponse.ok(getBookingByIdUseCase.execute(id));
    }

    @PostMapping("{id}/payment")
    public RestApiResponse<PaymentResponse> payment(@PathVariable Long id,
                                   QueueTokenRequest queueTokenRequest,
                                   @RequestBody PaymentRequest request) {
        return RestApiResponse.ok(payBookingUseCase.execute(id, request));
    }
}
