package hhplus.concert.api.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.INTERNAL_SERVER_ERROR, "the user is not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
