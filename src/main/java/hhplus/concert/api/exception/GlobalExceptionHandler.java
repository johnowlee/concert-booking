package hhplus.concert.api.exception;

import hhplus.concert.api.exception.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ExceptionResponse handleCustomException(RestApiException e) {
        return ExceptionResponse.from(e);
    }

    @ExceptionHandler(BindException.class)
    public ExceptionResponse bindException(BindException e) {
        return ExceptionResponse.from(e);
    }
}
