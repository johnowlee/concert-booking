package hhplus.concert.api.booking.dto.response.booking;

import hhplus.concert.domain.user.models.User;

public record BookingUserDto(Long userId,
                             String name) {
    public static BookingUserDto from(User user) {
        return new BookingUserDto(user.getId(), user.getName());
    }
}
