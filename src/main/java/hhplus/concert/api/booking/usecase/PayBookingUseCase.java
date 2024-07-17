package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.dto.response.payment.PaymentResponse;
import hhplus.concert.domain.balance.service.BalanceService;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.service.BookingManager;
import hhplus.concert.domain.payment.components.PaymentWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.api.common.ResponseResult.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayBookingUseCase {

    private final BookingReader bookingReader;
    private final PaymentWriter paymentWriter;
    private final BookingManager bookingManager;
    private final BalanceService balanceService;

    @Transactional
    public PaymentResponse execute(Long id, Long userId) {

        Booking booking = bookingReader.getBookingById(id);

        // 예약시간초과 검증
        booking.validateBookingDateTime();

        // 결제자 ID 검증
        booking.validatePayer(userId);

        // 잔액 use
        balanceService.use(booking);

        // 결제 내역 save
        paymentWriter.payBooking(booking);

        // 예약 완료
        booking.markAsComplete();

        // 좌석 예약
        bookingManager.reserveAllSeats(booking);

        return PaymentResponse.from(SUCCESS);
    }
}
