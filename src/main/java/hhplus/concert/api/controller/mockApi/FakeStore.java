package hhplus.concert.api.controller.mockApi;

import hhplus.concert.api.dto.request.BookingRequest;
import hhplus.concert.api.dto.request.PaymentRequest;
import hhplus.concert.api.dto.request.QueueTokenRequest;
import hhplus.concert.api.dto.request.UserRequest;
import hhplus.concert.api.dto.response.booking.BookingResponse;
import hhplus.concert.api.dto.response.booking.PaymentResponse;
import hhplus.concert.api.dto.response.ResponseResult;
import hhplus.concert.api.dto.response.concert.ConcertResponse;
import hhplus.concert.api.dto.response.concert.ConcertsResponse;
import hhplus.concert.api.dto.response.concert.SeatResponse;
import hhplus.concert.api.dto.response.user.QueueResponse;
import hhplus.concert.api.dto.response.user.UserResponse;
import hhplus.concert.domain.model.enums.BookingStatus;
import hhplus.concert.domain.model.enums.QueueStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FakeStore {

    public ConcertsResponse createConcerts() {

        List<ConcertResponse> concertsResponses = new ArrayList<>(List.of(
                new ConcertResponse(1, "concertA", LocalDateTime.now().plusDays(1), createSeats(1)),
                new ConcertResponse(2, "concertB", LocalDateTime.now().plusDays(2), createSeats(2)),
                new ConcertResponse(3, "concertC", LocalDateTime.now().plusDays(2), createSeats(3))
        ));
        return new ConcertsResponse(concertsResponses);
    }

    private static List<SeatResponse> createSeats(int batchNumber) {
        List<SeatResponse> seats = new ArrayList<>();
        final int seatsPerBatch = 50;
        int firstSeatNumber = (batchNumber - 1) * seatsPerBatch + 1;

        for (int i = 0; i < seatsPerBatch; i++) {
            String seatLabel = "A_" + (firstSeatNumber + i);
            seats.add(new SeatResponse(firstSeatNumber + i - 1, seatLabel, BookingStatus.INCOMPLETE));
        }

        return seats;
    }

    public QueueResponse createFakeQueue() {
        return new QueueResponse(UUID.randomUUID().toString(), 0);
    }

    public BookingResponse getBookingResponse(QueueTokenRequest queueTokenRequest, BookingRequest bookingRequest) {
        // 좌석 정보
        List<SeatResponse> seatResponse = new ArrayList<>();
        String[] seats = bookingRequest.seats().split(",");
        for (int i = 0; i<seats.length; i++) {
            seatResponse.add(new SeatResponse(i, seats[i], BookingStatus.COMPLETE));
        }
        return new BookingResponse(
                ResponseResult.SUCCESS,
                1,
                BookingStatus.COMPLETE,
                LocalDateTime.now(),
                new UserResponse(bookingRequest.userId(), "UserA", 100000),
                new ConcertResponse(bookingRequest.concertId(),"concertA",LocalDateTime.now().plusDays(5), seatResponse)
                );
    }

    public PaymentResponse getPaymentResponse(QueueTokenRequest queueTokenRequest, PaymentRequest paymentRequest) {
        List<SeatResponse> seatResponse = new ArrayList<>();
        String[] seats = "A_10,A_11".split(",");
        for (int i = 0; i<seats.length; i++) {
            seatResponse.add(new SeatResponse(i, seats[i], BookingStatus.COMPLETE));
        }
        return new PaymentResponse(
                ResponseResult.SUCCESS,
                new BookingResponse(
                    ResponseResult.SUCCESS,
                    paymentRequest.bookingId(),
                    BookingStatus.COMPLETE,
                    LocalDateTime.now(),
                    new UserResponse(paymentRequest.userId(), "UserA", 100000),
                    new ConcertResponse(10,"concertA",LocalDateTime.now().plusDays(5),seatResponse)
                )
        );
    }

    public UserResponse getBalance(long id) {
        return new UserResponse(id, "아무개", 10000);
    }

    public UserResponse chargeBalance(UserRequest userRequest) {
        return new UserResponse(userRequest.userId(), "아무개", userRequest.balance());
    }
}
