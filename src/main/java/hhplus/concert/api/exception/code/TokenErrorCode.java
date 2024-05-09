package hhplus.concert.api.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenErrorCode implements ErrorCode {
    NOT_FOUND_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "token does not exist"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    TokenErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
