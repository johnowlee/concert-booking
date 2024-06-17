package hhplus.concert.api.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BookingErrorCode implements ErrorCode {
    NOT_FOUND_BOOKING(HttpStatus.INTERNAL_SERVER_ERROR, "the booking is not found"),

    PROCESSING_BOOKING(HttpStatus.INTERNAL_SERVER_ERROR, "booking is processing in some other place"),
    ALREADY_BOOKED(HttpStatus.INTERNAL_SERVER_ERROR, "the seat is already booked"),

    EXPIRED_BOOKING_TIME(HttpStatus.INTERNAL_SERVER_ERROR, "booking time is expired")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    BookingErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
