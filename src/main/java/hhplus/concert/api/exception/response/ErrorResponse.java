package hhplus.concert.api.exception.response;

import hhplus.concert.api.exception.code.ErrorCode;

public record ErrorResponse(String code, String message) {
    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getMessage());
    }
}
