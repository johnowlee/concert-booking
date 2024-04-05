package hhplus.concert.api.mock.dto.response.booking;


import hhplus.concert.api.mock.dto.response.concert.ConcertDTO;
import hhplus.concert.api.mock.dto.response.user.UserDTO;

public record BookingResponse(BookingDTO booking,
                              UserDTO bookingUser,
                              ConcertDTO bookingConcert) {}
