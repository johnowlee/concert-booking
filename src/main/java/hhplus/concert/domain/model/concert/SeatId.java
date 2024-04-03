package hhplus.concert.domain.model.concert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SeatId implements Serializable {

    private String seatNo;

    private long concertId;
}
