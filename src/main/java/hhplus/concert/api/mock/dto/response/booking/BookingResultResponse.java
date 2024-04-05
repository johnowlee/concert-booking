package hhplus.concert.api.mock.dto.response.booking;

import hhplus.concert.api.mock.dto.response.ResponseResult;

import java.time.LocalDateTime;

public record BookingResultResponse(ResponseResult bookingResult,
                                    LocalDateTime bookingDatetime,
                                    String bookingUserName,
                                    BookingConcert concert) {}
