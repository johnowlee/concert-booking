package hhplus.concert.api.exception.response;

import hhplus.concert.api.exception.code.ErrorCode;
import org.springframework.validation.BindException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public record ErrorResponse(int status, String code, String message) {
    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                errorCode.name(),
                errorCode.getMessage()
        );
    }

    public static ErrorResponse from(BindException e) {
        return new ErrorResponse(
                BAD_REQUEST.value(),
                BAD_REQUEST.name(),
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
    }
}
