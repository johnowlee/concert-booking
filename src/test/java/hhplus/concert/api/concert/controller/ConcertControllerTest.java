package hhplus.concert.api.concert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.concert.usecase.GetConcertOptionUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionsUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.api.concert.usecase.response.ConcertOptionWithSeatsResponse;
import hhplus.concert.api.concert.usecase.response.ConcertOptionsResponse;
import hhplus.concert.api.concert.usecase.response.ConcertsResponse;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.queue.service.QueueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static hhplus.concert.domain.concert.models.SeatBookingStatus.AVAILABLE;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertController.class)
class ConcertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private GetConcertsUseCase getConcertsUseCase;

    @MockBean
    private GetConcertOptionsUseCase getConcertOptionsUseCase;

    @MockBean
    private GetConcertOptionUseCase getConcertOptionUseCase;

    @MockBean
    private QueueService queueService;

    @DisplayName("콘서트 목록 조회")
    @Test
    public void getConcerts() throws Exception {
        // given
        Concert concert = createConcert();
        List<Concert> concerts = new ArrayList<>(List.of(concert));

        ConcertsResponse concertsResponse = ConcertsResponse.from(concerts);
        given(getConcertsUseCase.execute()).willReturn(concertsResponse);

        // expected
        mockMvc.perform(get("/concerts")
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concerts[0].title").value("아이유 콘서트"))
                .andExpect(jsonPath("$.data.concerts[0].organizer").value("아이유"));
    }

    @DisplayName("콘서트 옵션 목록 조회")
    @Test
    public void getConcertOptions() throws Exception {
        // given
        ConcertOption concertOption = ConcertOption.builder()
                .place("월드컵경기장")
                .build();
        
        ConcertOptionsResponse concertOptionsResponse = ConcertOptionsResponse.from(List.of(concertOption));
        given(getConcertOptionsUseCase.execute(1L)).willReturn(concertOptionsResponse);

        // expected
        mockMvc.perform(get("/concerts/{id}/options", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concertOptions[0].place").value("월드컵경기장"));
    }

    @DisplayName("콘서트 옵션 단건 조회")
    @Test
    public void getConcertOption() throws Exception {
        // given
        Seat seat = createSeat("A1", AVAILABLE, null);
        ConcertOption concertOption = ConcertOption.builder()
                .place("월드컵경기장")
                .build();

        ConcertOptionWithSeatsResponse concertOptionResponse = ConcertOptionWithSeatsResponse.of(concertOption, List.of(seat));
        given(getConcertOptionUseCase.execute(1L)).willReturn(concertOptionResponse);

        // expected
        mockMvc.perform(get("/concerts/options/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concertOption.place").value("월드컵경기장"))
                .andExpect(jsonPath("$.data.seats[0].seatNo").value("A1"))
                .andExpect(jsonPath("$.data.seats[0].seatBookingStatus").value(AVAILABLE.name()));
    }

    private static Concert createConcert() {
        return Concert.builder()
                .title("아이유 콘서트")
                .organizer("아이유")
                .build();
    }

    private static Seat createSeat(String seatNo, SeatBookingStatus seatBookingStatus, ConcertOption concertOption) {
        return Seat.builder()
                .seatNo(seatNo)
                .seatBookingStatus(seatBookingStatus)
                .concertOption(concertOption)
                .build();
    }
}