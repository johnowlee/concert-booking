package hhplus.concert.core.queue.service;

import hhplus.concert.IntegrationTestSupport;

class RedisKeyEventServiceTest extends IntegrationTestSupport {

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
//    RedisKeyEventService redisKeyEventService;
//
//    @DisplayName("토큰을 만료하고 대기열에 대기자가 있으면 첫번째 대기자를 활성 유저로 추가한다.")
//    @Test
//    void expire() {
//        // given
//        String removeToken = "abc";
//        Set<String> firstWaiter = Set.of("qwer");
//
//        given(queueQueryService.getTokensFromSortedSetByRange(Key.WAITING, 0, 0)).willReturn(firstWaiter);
//
//        // when
//        redisKeyEventService.expire(removeToken);
//
//        // then
//        verify(queueCommandService, times(1)).removeActiveToken(any(Queue.class));
//        verify(queueCommandService, times(1)).removeWaitingToken(any(Queue.class));
//        verify(queueCommandService, times(1)).addActiveToken(any(Queue.class));
//        verify(queueCommandService, times(1)).setActiveTokenTtl(any(Queue.class), eq(queueMonitor));
//    }
//
//    @DisplayName("활성 유저의 토큰을 만료하고 만약 대기열에 대기자가 없으면 아무일도 일어나지 않는다.")
//    @Test
//    void expireWhenNoWaiter() {
//        // given
//        String removeToken = "abc";
//        Set<String> firstWaiter = null;
//
//        given(queueQueryService.getTokensFromSortedSetByRange(Key.WAITING, 0, 0)).willReturn(firstWaiter);
//
//        // when
//        redisKeyEventService.expire(removeToken);
//
//        // then
//        verify(queueCommandService, times(1)).removeActiveToken(any(Queue.class));
//        verify(queueCommandService, times(0)).removeWaitingToken(any(Queue.class));
//        verify(queueCommandService, times(0)).addActiveToken(any(Queue.class));
//        verify(queueCommandService, times(0)).setActiveTokenTtl(any(Queue.class), eq(queueMonitor));
//    }
}