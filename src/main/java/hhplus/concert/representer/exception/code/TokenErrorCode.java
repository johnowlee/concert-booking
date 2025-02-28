package hhplus.concert.representer.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenErrorCode implements ErrorCode {
    NOT_FOUND_TOKEN(HttpStatus.BAD_REQUEST, "token does not exist"),
    WAITING_TOKEN(HttpStatus.BAD_REQUEST, "token's status is WAITING"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    TokenErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
