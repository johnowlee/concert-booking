package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.dto.response.payment.PaymentResponse;
import hhplus.concert.domain.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.SeatPriceByGrade;
import hhplus.concert.domain.payment.components.PaymentWriter;
import hhplus.concert.domain.queue.components.QueGenerator;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueValidator;
import hhplus.concert.domain.queue.model.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.api.common.ResponseResult.FAILURE;
import static hhplus.concert.api.common.ResponseResult.SUCCESS;
import static hhplus.concert.domain.balance.models.TransactionType.*;
import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static hhplus.concert.domain.concert.models.SeatBookingStatus.BOOKED;
import static hhplus.concert.domain.queue.model.QueueStatus.EXPIRED;
import static hhplus.concert.domain.queue.model.QueueStatus.WAITING;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayBookingUseCase {

    private final QueueReader queueReader;
    private final QueueValidator queueValidator;
    private final QueGenerator queGenerator;
    private final BookingReader bookingReader;
    private final PaymentWriter paymentWriter;
    private final BalanceHistoryWriter balanceHistoryWriter;

    @Transactional
    public PaymentResponse excute(Long id, String queueId) {
        // 대기열 검증
        Queue queue = queueReader.getQueueById(queueId);
        if (queue == null) {
            throw new RuntimeException("대기열 Token이 존재하지 않습니다.");
        }

        // 예약시간초과 검증
        Booking booking = bookingReader.getBookingById(id);
        if (booking.isBookingDateTimeExpired()) {
            throw new RuntimeException("예약시간이 만료되었습니다. 예약을 다시 진행해 주세요.");
        }

        // 대기열 만료 검증
        if (queue.isExpired()) {
            // queue 생성
            queue = queGenerator.getQueue(queue.getUser());

            // 대기상태면? 실패-대기.
            if (queue.getStatus() == WAITING) {
                return PaymentResponse.from(FAILURE);
            }
        }

        // 잔액검증 및 user 잔액 update
        long amount = booking.getBookingSeats().size() * SeatPriceByGrade.A.getValue();
        queue.getUser().useBalance(amount);

        // 잔액내역 save
        balanceHistoryWriter.saveBalanceHistory(queue.getUser(), amount, USE);

        // 결제 내역 save
        paymentWriter.payBooking(booking, amount);

        // 예약 상태 update
        booking.changeBookingStatus(COMPLETE);

        // 좌석 예약상태 update
        booking.changeSeatsBookingStatus(BOOKED);

        // 대기열 만료
        queue.changeQueueStatus(EXPIRED);

        return PaymentResponse.from(SUCCESS);
    }
}
