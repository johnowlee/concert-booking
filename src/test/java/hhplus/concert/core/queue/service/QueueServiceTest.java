package hhplus.concert.core.queue.service;

import hhplus.concert.IntegrationTestSupport;

class QueueServiceTest extends IntegrationTestSupport {

//    @MockBean
//    QueueQueryService queueQueryService;
//
//    @MockBean
//    QueueCommandService queueCommandService;
//
//    @MockBean
//    QueueMonitor queueMonitor;
//
//    @Autowired
//    QueueService queueService;
//
//    @DisplayName("활성 유저의 토큰이면 ACTIVE 토큰을 반환한다.")
//    @Test
//    void getQueueByTokenWhenActiveToken() {
//        // given
//        String token = "abc";
//        given(queueQueryService.isActiveToken(ACTIVE, token)).willReturn(true);
//
//        // when
//        Queue result = queueService.getQueueByToken(token);
//
//        // then
//        assertThat(result.getToken()).isEqualTo("abc");
//        assertThat(result.getKey()).isEqualTo(ACTIVE);
//    }
//
//    @DisplayName("대기 유저의 토큰이면 WAITING 토큰을 반환한다.")
//    @Test
//    void getQueueByTokenWhenWaitingToken() {
//        // given
//        String token = "abc";
//        Long rank = 10L;
//        given(queueQueryService.getWaitingNumber(WAITING, token)).willReturn(rank);
//        given(queueQueryService.isActiveToken(ACTIVE, token)).willReturn(false);
//        given(queueQueryService.getTokenScoreFromSortedSet(WAITING, token)).willReturn(1.0);
//
//        // when
//        Queue result = queueService.getQueueByToken(token);
//
//        // then
//        assertThat(result.getToken()).isEqualTo("abc");
//        assertThat(result.getKey()).isEqualTo(WAITING);
//        assertThat(result.getWaitingNumber()).isEqualTo(11);
//        verify(queueQueryService, times(1)).getWaitingNumber(WAITING, "abc");
//
//    }
//
//    @DisplayName("토큰이 유효하지 않으면 예외가 발생한다.")
//    @Test
//    void getQueueByTokenWithInvalidToken() {
//        // given
//        String token = "abc";
//        given(queueQueryService.isActiveToken(ACTIVE, token)).willReturn(false);
//        given(queueQueryService.getTokenScoreFromSortedSet(WAITING, token)).willReturn(null);
//
//        // when & then
//        assertThatThrownBy(() -> queueService.getQueueByToken("abc"))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(NOT_FOUND_TOKEN.getMessage());
//    }
//
//    @DisplayName("현재 활성 토큰의 갯수가 활성 최대 수용치 보다 적으면 활성 토큰이 발급된다.")
//    @Test
//    void createNewQueueWhenAccessible() {
//        // given
//        String token = "abc";
//        long score = 123456L;
//        given(queueQueryService.getActiveUserCount(ACTIVE)).willReturn(49L);
//        given(queueMonitor.getMaxActiveUserCount()).willReturn(50);
//
//        // when
//        Queue result = queueService.createNewQueue(token, score);
//
//        // then
//        assertThat(49L).isLessThan(queueMonitor.getMaxActiveUserCount());
//        assertThat(result.getToken()).isEqualTo("abc");
//        assertThat(result.getKey()).isEqualTo(ACTIVE);
//        then(queueCommandService).should(times(1)).addActiveToken(any(Queue.class));
//        then(queueCommandService).should(times(1)).createActiveKey(any(Queue.class), eq(queueMonitor));
//    }
//
//    @DisplayName("현재 활성 토큰의 갯수가 활성 최대 수용치 보다 크거나 같으면 대기 토큰이 발급된다.")
//    @Test
//    void createNewQueueWhenNotAccessible() {
//        // given
//        String token = "abc";
//        long score = 123456L;
//        given(queueQueryService.getActiveUserCount(Key.ACTIVE)).willReturn(50L);
//        given(queueMonitor.getMaxActiveUserCount()).willReturn(50);
//
//        // when
//        Queue result = queueService.createNewQueue(token, score);
//
//        // then
//        assertThat(result.getToken()).isEqualTo("abc");
//        assertThat(result.getKey()).isEqualTo(WAITING);
//        then(queueCommandService).should(times(1)).addWaitingToken(any(Queue.class));
//    }
//
//    @DisplayName("활성 토큰이면 예외가 발생하지 않는다.")
//    @Test
//    void validateToken() {
//        // given
//        String token = "abc";
//        Key key = ACTIVE;
//        given(queueQueryService.getTokenScoreFromSortedSet(WAITING, token)).willReturn(null);
//        given(queueQueryService.isActiveToken(key, token)).willReturn(true);
//
//        // when
//        queueService.validateToken(token);
//
//        // then
//        then(queueQueryService).should(times(1)).getTokenScoreFromSortedSet(WAITING, token);
//        then(queueQueryService).should(times(1)).isActiveToken(key, token);
//    }
//
//    @DisplayName("대기 토큰이면 예외 발생한다.")
//    @Test
//    void validateTokenWithWaitingToken() {
//        // given
//        String token = "abc";
//        given(queueQueryService.getTokenScoreFromSortedSet(WAITING, token)).willReturn(1.0);
//
//        // when & then
//        assertThatThrownBy(() -> queueService.validateToken(token))
//                        .isInstanceOf(RestApiException.class)
//                        .hasMessage(WAITING_TOKEN.getMessage());
//        then(queueQueryService).should(times(1)).getTokenScoreFromSortedSet(WAITING, token);
//    }
//
//    @DisplayName("유효하지 않은 토큰이면 예외 발생한다.")
//    @Test
//    void validateTokenWithNotFoundToken() {
//        // given
//        String token = "abc";
//        Key key = ACTIVE;
//        given(queueQueryService.getTokenScoreFromSortedSet(WAITING, token)).willReturn(null);
//        given(queueQueryService.isActiveToken(key, token)).willReturn(false);
//
//        // when & then
//        assertThatThrownBy(() -> queueService.validateToken(token))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(NOT_FOUND_TOKEN.getMessage());
//        then(queueQueryService).should(times(1)).getTokenScoreFromSortedSet(WAITING, token);
//        then(queueQueryService).should(times(1)).isActiveToken(key, token);
//    }
}