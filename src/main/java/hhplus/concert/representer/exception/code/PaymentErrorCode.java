package hhplus.concert.representer.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PaymentErrorCode implements ErrorCode {
    PAYABLE_TIME_OVER(HttpStatus.INTERNAL_SERVER_ERROR, "pay limit time is over"),
    INVALID_PAYER(HttpStatus.INTERNAL_SERVER_ERROR, "payer's ID and booker's ID is different")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    PaymentErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
