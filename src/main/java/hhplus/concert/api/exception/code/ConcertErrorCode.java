package hhplus.concert.api.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ConcertErrorCode implements ErrorCode {
    CONCERT_OPTION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid concert option id"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ConcertErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
