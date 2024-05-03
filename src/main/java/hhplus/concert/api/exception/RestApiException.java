package hhplus.concert.api.exception;

import hhplus.concert.api.exception.code.ErrorCode;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public RestApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
