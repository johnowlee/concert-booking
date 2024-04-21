package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.dto.response.payment.PaymentResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayBookingUseCase {

    private final BookingReader bookingReader;

    public PaymentResponse excute(String queueTokeinId, Long id) {
        // 대기열 검증

        // 잔액검증

        // 결제 내역

        // 잔액내역, 잔액 update

        // 예약 상태 update

        return null;
    }
}
