package hhplus.concert.representer.api.concert;

import hhplus.concert.representer.api.concert.ConcertController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(ConcertController.class)
class ConcertControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    private GetConcertsUseCase getConcertsUseCase;
//
//    @MockBean
//    private GetConcertOptionsByConcertIdUseCase getConcertOptionsByConcertIdUseCase;
//
//    @MockBean
//    private GetConcertOptionByIdUseCase getConcertOptionByIdUseCase;
//
//    @MockBean
//    private QueueService queueService;
//
//    @DisplayName("콘서트 목록 조회")
//    @Test
//    public void getConcerts() throws Exception {
//        // given
//        Concert concert = createConcert();
//        List<Concert> concerts = new ArrayList<>(List.of(concert));
//
//        ConcertsResponse concertsResponse = ConcertsResponse.from(concerts);
//        given(getConcertsUseCase.execute()).willReturn(concertsResponse);
//
//        // expected
//        mockMvc.perform(get("/concerts")
//                        .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.concerts[0].title").value("아이유 콘서트"))
//                .andExpect(jsonPath("$.data.concerts[0].organizer").value("아이유"));
//    }
//
//    @DisplayName("콘서트 옵션 목록 조회")
//    @Test
//    public void getConcertOptions() throws Exception {
//        // given
//        ConcertOption concertOption = ConcertOption.builder()
//                .place("월드컵경기장")
//                .build();
//
//        ConcertOptionsResponse concertOptionsResponse = ConcertOptionsResponse.from(List.of(concertOption));
//        given(getConcertOptionsByConcertIdUseCase.execute(1L)).willReturn(concertOptionsResponse);
//
//        // expected
//        mockMvc.perform(get("/concerts/{id}/options", 1L)
//                        .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.concertOptions[0].place").value("월드컵경기장"));
//    }
//
//    @DisplayName("콘서트 옵션 단건 조회")
//    @Test
//    public void getConcertOption() throws Exception {
//        // given
//        Seat seat = createSeat("A1", AVAILABLE, null);
//        ConcertOption concertOption = ConcertOption.builder()
//                .place("월드컵경기장")
//                .build();
//
//        ConcertOptionWithSeatsResponse concertOptionResponse = ConcertOptionWithSeatsResponse.of(concertOption, List.of(seat));
//        given(getConcertOptionByIdUseCase.execute(1L)).willReturn(concertOptionResponse);
//
//        // expected
//        mockMvc.perform(get("/concerts/options/{id}", 1L)
//                        .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.concertOption.place").value("월드컵경기장"))
//                .andExpect(jsonPath("$.data.seats[0].seatNo").value("A1"))
//                .andExpect(jsonPath("$.data.seats[0].seatBookingStatus").value(AVAILABLE.name()));
//    }
//
//    private static Concert createConcert() {
//        return Concert.builder()
//                .title("아이유 콘서트")
//                .organizer("아이유")
//                .build();
//    }
//
//    private static Seat createSeat(String seatNo, SeatBookingStatus seatBookingStatus, ConcertOption concertOption) {
//        return Seat.builder()
//                .seatNo(seatNo)
//                .seatBookingStatus(seatBookingStatus)
//                .concertOption(concertOption)
//                .build();
//    }
}