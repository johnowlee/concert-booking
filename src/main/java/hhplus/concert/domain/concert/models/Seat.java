package hhplus.concert.domain.concert.models;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Seat {

    private Long id;
    private String seatNo;

    @Builder
    private Seat(Long id, String seatNo) {
        this.id = id;
        this.seatNo = seatNo;
    }
}
