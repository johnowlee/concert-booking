package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.dto.response.payment.PaymentResponse;
import hhplus.concert.distribution.RedisQueueService;
import hhplus.concert.distribution.TokenKey;
import hhplus.concert.domain.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.SeatPriceByGrade;
import hhplus.concert.domain.payment.components.PaymentWriter;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.api.common.ResponseResult.FAILURE;
import static hhplus.concert.api.common.ResponseResult.SUCCESS;
import static hhplus.concert.domain.balance.models.TransactionType.USE;
import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static hhplus.concert.domain.concert.models.SeatBookingStatus.BOOKED;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayBookingUseCase {

    private final BookingReader bookingReader;
    private final PaymentWriter paymentWriter;
    private final BalanceHistoryWriter balanceHistoryWriter;
    private final RedisQueueService redisQueueService;
    private final UserReader userReader;

    @Transactional
    public PaymentResponse execute(Long id, String token, Long userId) {
        // 대기열 검증
        String key = redisQueueService.findToken(token).key();
        if (key.equals(TokenKey.WAITING.toString())) {
            return PaymentResponse.from(FAILURE);
        }

        Booking booking = bookingReader.getBookingById(id);

        // 예약시간초과 검증
        if (booking.isBookingDateTimeExpired()) {
            throw new RuntimeException("예약시간이 만료되었습니다. 예약을 다시 진행해 주세요.");
        }

        User user = userReader.getUserById(userId);

        // 잔액검증 및 user 잔액 update
        long amount = calculateAmount(booking);
        user.useBalance(amount);

        // 잔액내역 save
        balanceHistoryWriter.saveBalanceHistory(user, amount, USE);

        // 결제 내역 save
        paymentWriter.payBooking(booking, amount);

        // 예약 상태 update
        booking.changeBookingStatus(COMPLETE);

        // 좌석 예약상태 update
        booking.changeSeatsBookingStatus(BOOKED);

        return PaymentResponse.from(SUCCESS);
    }

    private static int calculateAmount(Booking booking) {
        return booking.getBookingSeats().size() * SeatPriceByGrade.A.getValue();
    }
}
