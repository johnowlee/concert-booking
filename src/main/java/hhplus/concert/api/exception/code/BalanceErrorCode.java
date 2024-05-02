package hhplus.concert.api.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BalanceErrorCode implements ErrorCode {
    FAILED_CHARGE(HttpStatus.INTERNAL_SERVER_ERROR, "point charge error"),
    FAILED_USE(HttpStatus.INTERNAL_SERVER_ERROR, "point use error")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    BalanceErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
