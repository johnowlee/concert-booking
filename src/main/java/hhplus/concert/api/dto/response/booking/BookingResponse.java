package hhplus.concert.api.dto.response.booking;

import hhplus.concert.api.dto.response.ResponseResult;
import hhplus.concert.api.dto.response.concert.ConcertResponse;
import hhplus.concert.api.dto.response.user.UserResponse;
import hhplus.concert.domain.model.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingResponse(ResponseResult bookingResult,
                              long bookingId,
                              BookingStatus bookingStatus,
                              LocalDateTime bookingDatetime,
                              UserResponse user,
                              ConcertResponse concert) {}
