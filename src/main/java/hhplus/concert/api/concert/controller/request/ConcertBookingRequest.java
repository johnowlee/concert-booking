package hhplus.concert.api.concert.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record ConcertBookingRequest(

        @NotNull(message = "유저 아이디는 필수입니다.")
        @Positive(message = "아이디가 부적합 합니다.")
        @JsonProperty("user_id") Long userId,

        @NotBlank(message = "예와 좌석 아이디는 필수입니다.")
        @ValidSeatIds
        @JsonProperty("seat_id") String seatId
) {

    public List<Long> seatIds() {
        return Arrays.stream(seatId.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
    }
}
