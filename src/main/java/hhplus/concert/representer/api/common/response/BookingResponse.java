package hhplus.concert.representer.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.models.BookingStatus;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.models.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@JsonInclude(NON_NULL)
public record BookingResponse(
        Long id,
        BookingStatus bookingStatus,
        LocalDateTime bookingDateTime,
        String concertTitle,
        UserResponse booker,
        ConcertOptionResponse concertOption,

        List<SeatResponse> seats
) {

    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getBookingStatus(),
                booking.getBookingDateTime(),
                booking.getConcertTitle(),
                createUserResponse(booking),
                createConcertOptionResponse(booking),
                createSeatsResponse(booking)
        );
    }

    private static UserResponse createUserResponse(Booking booking) {
        User booker = booking.getUser();
        return UserResponse.builder()
                .id(booker.getId())
                .name(booker.getName())
                .build();
    }

    private static ConcertOptionResponse createConcertOptionResponse(Booking booking) {
        ConcertOption concertOption = getConcertOptionFrom(booking);
        return ConcertOptionResponse.builder()
                .id(concertOption.getId())
                .concertDateTime(concertOption.getConcertDateTime())
                .place(concertOption.getPlace())
                .build();
    }

    private static List<SeatResponse> createSeatsResponse(Booking booking) {
        return getBookingSeatsFrom(booking).stream()
                .map(bs -> createSeatResponse(bs))
                .collect(Collectors.toList());
    }

    private static ConcertOption getConcertOptionFrom(Booking booking) {
        return getBookingSeatsFrom(booking).get(0).getSeat().getConcertOption();
    }

    private static List<BookingSeat> getBookingSeatsFrom(Booking booking) {
        return booking.getBookingSeats();
    }

    private static SeatResponse createSeatResponse(BookingSeat bookingSeat) {
        Seat seat = bookingSeat.getSeat();
        return SeatResponse.builder()
                .id(seat.getId())
                .seatNo(seat.getSeatNo())
                .grade(seat.getGrade())
                .build();
    }

}
