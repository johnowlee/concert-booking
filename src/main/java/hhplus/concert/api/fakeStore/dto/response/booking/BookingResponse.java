package hhplus.concert.api.fakeStore.dto.response.booking;


import hhplus.concert.api.fakeStore.dto.response.concert.ConcertDTO;
import hhplus.concert.api.fakeStore.dto.response.user.UserDTO;

public record BookingResponse(BookingDTO booking,
                              UserDTO bookingUser,
                              ConcertDTO bookingConcert) {}
