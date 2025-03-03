package hhplus.concert.representer.api.queue;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(QueueController.class)
class QueueControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    FindTokenUseCase findTokenUseCase;
//
//    @MockBean
//    CreateTokenUseCase createQueueToken;
//
//    @MockBean
//    QueueControllerMapper mapper;
//
//    @MockBean
//    private QueueService queueService;
//
//    @DisplayName("ACTIVE 토큰을 조회한다.")
//    @Test
//    public void findTokenWithActiveToken() throws Exception {
//        // given
//        String token = "abc123";
//        Queue queue = Queue.createActiveQueue(token);
//        given(findTokenUseCase.execute(new QueueTokenRequest(token))).willReturn(queue);
//
//        // when & then
//        mockMvc.perform(get("/queue")
//                        .contentType(APPLICATION_JSON)
//                        .header("Queue-Token", token)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.status").value("OK"))
//                .andExpect(jsonPath("$.message").value("OK"))
//                .andExpect(jsonPath("$.data.token").value("abc123"))
//                .andExpect(jsonPath("$.data.key").value("ACTIVE"));
//    }
//
//    @DisplayName("WAITING 토큰을 조회한다.")
//    @Test
//    public void findTokenWithWaitingToken() throws Exception {
//        // given
//        String token = "abc123";
//        Queue queue = Queue.createWaitingQueue(token, 5);
//        given(findTokenUseCase.execute(new QueueTokenRequest(token))).willReturn(queue);
//
//        // when & then
//        mockMvc.perform(get("/queue")
//                        .contentType(APPLICATION_JSON)
//                        .header("Queue-Token", token)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.status").value("OK"))
//                .andExpect(jsonPath("$.message").value("OK"))
//                .andExpect(jsonPath("$.data.token").value("abc123"))
//                .andExpect(jsonPath("$.data.key").value("WAITING"))
//                .andExpect(jsonPath("$.data.waitingNumber").value(5));
//    }
//
//    @DisplayName("ACTIVE 토큰을 생성한다.")
//    @Test
//    public void createTokenWhenActive() throws Exception {
//        // given
//        String token = "abc123";
//        Queue queue = Queue.createActiveQueue(token);
//        given(createQueueToken.execute()).willReturn(queue);
//
//        // when & then
//        mockMvc.perform(post("/queue")
//                        .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.status").value("OK"))
//                .andExpect(jsonPath("$.message").value("OK"))
//                .andExpect(jsonPath("$.data.token").value("abc123"))
//                .andExpect(jsonPath("$.data.key").value("ACTIVE"));
//    }
//
//    @DisplayName("WAITING 토큰을 생성한다.")
//    @Test
//    public void createTokenWhenWaiting() throws Exception {
//        // given
//        String token = "abc123";
//        Queue queue = Queue.createWaitingQueue(token, 5);
//        given(createQueueToken.execute()).willReturn(queue);
//
//        // when & then
//        mockMvc.perform(post("/queue")
//                        .contentType(APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.status").value("OK"))
//                .andExpect(jsonPath("$.message").value("OK"))
//                .andExpect(jsonPath("$.data.token").value("abc123"))
//                .andExpect(jsonPath("$.data.key").value("WAITING"))
//                .andExpect(jsonPath("$.data.waitingNumber").value(5));
//    }
}