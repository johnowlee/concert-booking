package hhplus.concert.api.exception.response;

import hhplus.concert.api.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;

public record ErrorResponse(String code, String message) {
    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getMessage());
    }

    public static ErrorResponse from(BindException e) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
    }
}
