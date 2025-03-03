package hhplus.concert.application.user.data.command;

import java.util.List;

public record ConcertBookingCommand(List<Long> seatIds) {
}
