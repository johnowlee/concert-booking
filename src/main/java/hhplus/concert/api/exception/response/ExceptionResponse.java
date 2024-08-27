package hhplus.concert.api.exception.response;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;

import static org.springframework.http.HttpStatus.*;

public record ExceptionResponse(int code, HttpStatus status, String name, String message) {

    public static ExceptionResponse of(int code, HttpStatus status, String name, String message) {
        return new ExceptionResponse(code, status, name, message);
    }

    public static ExceptionResponse of(HttpStatus status, String name, String message) {
        return of(status.value(), status, name, message);
    }

    public static ExceptionResponse from(RestApiException e) {
        return from(e.getErrorCode());
    }

    public static ExceptionResponse from(BindException e) {
        return of(BAD_REQUEST, "INVALID_FIELD_VALUE", getBindingErrorMessage(e));
    }

    private static ExceptionResponse from(ErrorCode errorCode) {
        return of(errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus(),
                errorCode.name(),
                errorCode.getMessage()
        );
    }

    private static String getBindingErrorMessage(BindException e) {
        return e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
}
