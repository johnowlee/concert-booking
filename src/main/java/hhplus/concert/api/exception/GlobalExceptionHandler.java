package hhplus.concert.api.exception;

import hhplus.concert.api.exception.code.ErrorCode;
import hhplus.concert.api.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException e) {
        return createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> bindException(BindException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.from(e));
    }

    private static ResponseEntity<Object> createErrorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.from(errorCode));
    }
}
