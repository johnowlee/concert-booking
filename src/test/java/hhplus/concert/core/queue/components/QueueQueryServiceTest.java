package hhplus.concert.core.queue.components;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QueueQueryServiceTest {

//    @InjectMocks
//    QueueQueryService queueQueryService;
//
//    @Mock
//    QueueQueryPort queueQueryPort;
//
//    @Mock
//    QueueMonitor queueMonitor;
//
//    @DisplayName("활성 유저 key에 등록된 토큰 수를 반환한다.")
//    @Test
//    void getTokenCountFromSet() {
//        // given
//        Long activeUserCount = 30L;
//        Key key = ACTIVE;
//        given(queueQueryPort.getTokenSizeFromSet(key.getKeyName())).willReturn(activeUserCount);
//
//        // when
//        Long result = queueQueryService.getActiveUserCount(key);
//
//        // then
//        assertThat(result).isEqualTo(30L);
//    }
//
//    @DisplayName("토큰이 활성 유저에 포함되어 있으면 true를 반환한다.")
//    @Test
//    void isActiveToken() {
//        // given
//        String token = "abc123";
//        String activeUserKey = ACTIVE.getKeyName();
//        given(queueQueryPort.doseTokenBelongToSet(activeUserKey, token)).willReturn(true);
//
//        // when
//        Boolean result = queueQueryService.isActiveToken(ACTIVE, token);
//
//        // then
//        assertThat(result).isTrue();
//        verify(queueQueryPort).doseTokenBelongToSet(activeUserKey, "abc123");
//    }
//
//    @DisplayName("토큰이 활성 유저에 포함되어 있지 않으면 false 반환한다.")
//    @Test
//    void isActiveTokenIfNotExists() {
//        // given
//        String token = "abc123";
//        String activeUserKey = ACTIVE.getKeyName();
//        given(queueQueryPort.doseTokenBelongToSet(activeUserKey, token)).willReturn(false);
//
//        // when
//        Boolean result = queueQueryService.isActiveToken(ACTIVE, token);
//
//        // then
//        assertThat(result).isFalse();
//        verify(queueQueryPort, times(1)).doseTokenBelongToSet(eq(activeUserKey), eq("abc123"));
//    }
//
//    @DisplayName("Sorted Set 토큰의 스코어를 조회 한다.")
//    @Test
//    void getTokenScoreFromSortedSet() {
//        // given
//        Double score = 1.0;
//        String token = "abc123";
//        Key key = Key.WAITING;
//        given(queueQueryPort.getTokenScoreFromSortedSet(key.getKeyName(), token)).willReturn(score);
//
//        // when
//        Double result = queueQueryService.getTokenScoreFromSortedSet(key, token);
//
//        // then
//        Assertions.assertThat(result).isEqualTo(1.0);
//    }
//
//    @DisplayName("Sorted Set에 토큰이 없으면 스코어는 null을 반환 한다.")
//    @Test
//    void getTokenScoreFromSortedSetWithNotExistedToken() {
//        // given
//        Double score = null;
//        String token = "abc123";
//        Key key = Key.WAITING;
//        given(queueQueryPort.getTokenScoreFromSortedSet(key.getKeyName(), token)).willReturn(score);
//
//        // when
//        Double result = queueQueryService.getTokenScoreFromSortedSet(key, token);
//
//        // then
//        Assertions.assertThat(result).isNull();
//    }
//
//    @DisplayName("대기 Sorted Set에서 토큰의 rank를 조회한다.")
//    @Test
//    void getTokenRankFromSortedSet() {
//        // given
//        String token = "abc123";
//        Key key = WAITING;
//        given(queueQueryPort.getTokenRankFromSortedSet(key.getKeyName(), token)).willReturn(1L);
//
//        // when
//        Long result = queueQueryService.getWaitingNumber(key, token);
//
//        // then
//        assertThat(result).isEqualTo(1L);
//    }
//
//    @DisplayName("대기 Sorted Set에서 존재하지 않는 토큰의 rank를 조회 시 null을 반환한다.")
//    @Test
//    void getTokenRankFromSortedSetWithNotExistedToken() {
//        // given
//        String token = "abc123";
//        Key key = WAITING;
//        given(queueQueryPort.getTokenRankFromSortedSet(key.getKeyName(), token)).willReturn(null);
//
//        // when
//        Long result = queueQueryService.getWaitingNumber(key, token);
//
//        // then
//        assertThat(result).isNull();
//    }
//
//    @DisplayName("대기 Sorted Set의 토큰들을 범위에 따라 조회한다.")
//    @Test
//    void getTokensFromSortedSetByRange() {
//        // given
//        Key key = WAITING;
//        long start = 0;
//        long end = 1;
//
//        String token1 = "abc";
//        String token2 = "def";
//        Set<String> tokens = Set.of(token1, token2);
//
//        given(queueQueryPort.getTokensFromSortedSetByRange(key.getKeyName(), start, end)).willReturn(tokens);
//
//        // when
//        Set<String> result = queueQueryService.getTokensFromSortedSetByRange(key, start, end);
//
//        // then
//        Assertions.assertThat(result).hasSize(2)
//                .contains(
//                        token1, token2
//                );
//    }
}