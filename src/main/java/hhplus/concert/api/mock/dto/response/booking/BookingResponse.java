package hhplus.concert.api.mock.dto.response.booking;

import hhplus.concert.api.mock.dto.response.ResponseResult;
import hhplus.concert.api.mock.dto.response.concert.ConcertWithSeatsResponse;
import hhplus.concert.api.mock.dto.response.user.UserResponse;
import hhplus.concert.domain.booking.model.BookingStatus;

import java.time.LocalDateTime;

public record BookingResponse(ResponseResult bookingResult,
                              long bookingId,
                              BookingStatus bookingStatus,
                              LocalDateTime bookingDatetime,
                              UserResponse user,
                              ConcertWithSeatsResponse concert) {}
