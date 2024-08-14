package hhplus.concert.api.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BookingErrorCode implements ErrorCode {
    NOT_FOUND_BOOKING(HttpStatus.INTERNAL_SERVER_ERROR, "the booking is not found"),

    PENDING_BOOKING(HttpStatus.INTERNAL_SERVER_ERROR, "booking is pending in some other place"),
    ALREADY_BOOKED(HttpStatus.INTERNAL_SERVER_ERROR, "the seat is already booked"),

    EXPIRED_BOOKING_TIME(HttpStatus.INTERNAL_SERVER_ERROR, "booking time is expired"),

    INVALID_PAYER(HttpStatus.INTERNAL_SERVER_ERROR, "payer's ID and booker's ID is different")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    BookingErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
