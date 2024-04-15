package hhplus.concert.api.fakeStore;

import hhplus.concert.api.fakeStore.dto.request.BookingRequest;
import hhplus.concert.api.fakeStore.dto.request.QueueTokenRequest;
import hhplus.concert.api.fakeStore.dto.request.UserRequest;
import hhplus.concert.api.fakeStore.dto.response.booking.*;
import hhplus.concert.api.fakeStore.dto.response.ResponseResult;
import hhplus.concert.api.fakeStore.dto.response.concert.*;
import hhplus.concert.api.fakeStore.dto.response.user.QueueResponse;
import hhplus.concert.api.fakeStore.dto.response.user.UserDTO;
import hhplus.concert.api.fakeStore.dto.response.user.UserWithBalanceResponse;
import hhplus.concert.api.fakeStore.dto.response.user.chargeBalanceResponse;
import hhplus.concert.entities.booking.BookingStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class FakeStore {

    public ConcertsResponse createConcerts() {

        List<ConcertResponse> concertsResponses = new ArrayList<>(List.of(
                new ConcertResponse(1, "concertA", LocalDateTime.now().plusDays(1), 50),
                new ConcertResponse(2, "concertB", LocalDateTime.now().plusDays(2), 50),
                new ConcertResponse(3, "concertC", LocalDateTime.now().plusDays(2), 50)
        ));
        return new ConcertsResponse(concertsResponses);
    }

    public ConcertWithSeatsResponse createConcert(long concertId) {
        return new ConcertWithSeatsResponse(concertId, "concertA", LocalDateTime.now().plusDays(1), createSeats(1));
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

    public BookingResultResponse getBookingResponse(QueueTokenRequest queueTokenRequest, long concertId, BookingRequest bookingRequest) {
        // 좌석 정보
        List<String> seatNoList = getSeats(bookingRequest.seats());
        return new BookingResultResponse(
                ResponseResult.SUCCESS,
                LocalDateTime.now(),
                "아무개",
                new BookingConcert("concertA",LocalDateTime.now().plusDays(5), seatNoList)
                );
    }

    private static List<String> getSeats(String seats) {
        return new ArrayList<>(Arrays.asList(seats.split(",")));
    }

    public PaymentResponse getPaymentResponse(QueueTokenRequest queueTokenRequest, long bookingId) {
        return new PaymentResponse(ResponseResult.SUCCESS);
    }

    public UserWithBalanceResponse getBalance(long id) {
        return new UserWithBalanceResponse(id, "아무개", 10000);
    }

    public chargeBalanceResponse chargeBalance(long userId, UserRequest userRequest) {
        return new chargeBalanceResponse(ResponseResult.SUCCESS, userRequest.balance());
    }

    public List<BookingsDTO> createBookings(long id) {
        List<BookingsDTO> objects = new ArrayList<>();
        objects.add(new BookingsDTO(
                new BookingDTO(1, BookingStatus.INCOMPLETE, LocalDateTime.now().plusDays(2)),
                new BookingConcert("concertA", LocalDateTime.now().plusDays(5), getSeats("A_13,B_22,B_23"))
        ));
        objects.add(new BookingsDTO(
                new BookingDTO(2, BookingStatus.COMPLETE, LocalDateTime.now().plusDays(8)),
                new BookingConcert("concertC", LocalDateTime.now().plusDays(5), getSeats("A_4,A_5"))
        ));
        return objects;
    }

    public BookingResponse createBooking(long bookingId) {
        ArrayList<SeatDTO> seats = new ArrayList<>();
        seats.add(new SeatDTO(1, "A_3"));
        seats.add(new SeatDTO(2, "A_4"));
        return new BookingResponse(
                new BookingDTO(bookingId, BookingStatus.INCOMPLETE, LocalDateTime.now().minusDays(2)),
                new UserDTO(1, "userA"),
                new ConcertDTO(1, "concertA", LocalDateTime.now().plusDays(3), seats)
        );
    }
}
