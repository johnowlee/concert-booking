package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.api.concert.dto.response.concertBooking.BookingResultResponse;
import hhplus.concert.domain.booking.components.BookingWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.components.SeatReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.queue.components.QueGenerator;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueValidator;
import hhplus.concert.domain.queue.model.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hhplus.concert.domain.queue.model.QueueStatus.WAITING;

@Repository
@RequiredArgsConstructor
public class BookConcertUseCase {

    private final ConcertOptionReader concertOptionReader;
    private final BookingWriter bookingWriter;
    private final SeatReader seatReader;
    private final QueueReader queueReader;
    private final QueGenerator queGenerator;
    private final QueueValidator queueValidator;

    @Transactional
    public BookingResultResponse excute(String queueId, ConcertBookingRequest request) {
        // 1. queue 조회
        // 대기열 검증
        Queue queue = queueReader.getQueueById(queueId);
        if (queue == null) {
            throw new RuntimeException("대기열 Token이 존재하지 않습니다.");
        }

        // 대기열 만료 검증
        if (queueValidator.isExpired(queue)) {
            // queue 생성
            queue = queGenerator.getQueue(queue.getUser());
            // 대기상태면? 실패-대기.
            if (queue.getStatus() == WAITING) {
                return BookingResultResponse.fail();
            }
        }

        // 2. 콘서트 옵션 id로 콘서트 옵션 조회
        ConcertOption concertOption = concertOptionReader.getConcertOptionById(request.concertOptionId());
        // 3. 좌석정보 조회
        List<Seat> seats = seatReader.getSeatsByIds(request.parsedSeatIds());
        // 4. 예약테이블 저장
        Booking savedBooking = bookingWriter.bookConcert(Booking.buildBooking(concertOption, queue.getUser()));
        // 5. 예약좌석매핑 테이블 저장
        bookingWriter.saveAllBookingSeat(BookingSeat.createBookingSeats(seats, savedBooking));

        return BookingResultResponse.succeed(queue.getUser(), savedBooking, concertOption, seats);
    }
}
