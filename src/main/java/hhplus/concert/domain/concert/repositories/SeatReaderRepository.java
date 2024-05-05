package hhplus.concert.domain.concert.repositories;

import hhplus.concert.domain.concert.models.Seat;

import java.util.List;

public interface SeatReaderRepository {

    public List<Seat> getSeatsByIds(List<Long> ids);
}
