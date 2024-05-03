package hhplus.concert.api.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BookingErrorCode implements ErrorCode {
    PROCESSING_BOOKING(HttpStatus.INTERNAL_SERVER_ERROR, "booking is processing in some other place"),
    ALREADY_BOOKED(HttpStatus.INTERNAL_SERVER_ERROR, "the seat is already booked")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    BookingErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
