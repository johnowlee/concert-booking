package hhplus.concert.domain.queue.support;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.api.exception.code.TokenErrorCode.WAITING_TOKEN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class TokenValidatorTest extends IntegrationTestSupport {

    @MockBean
    QueueReader queueReader;

    @Autowired
    TokenValidator tokenValidator;

    @DisplayName("활성 유저의 토큰이면 검증에 통과한다.")
    @Test
    void validateToken() {
        // given
        String token = "abc123";
        given(queueReader.isWaitingToken(token)).willReturn(false);
        given(queueReader.isActiveToken(token)).willReturn(true);

        // when & then
        tokenValidator.validateToken(token);
    }

    @DisplayName("대기 유저의 토큰이면 예외가 발생한다.")
    @Test
    void validateTokenWithWaitingToken() {
        // given
        String token = "abc123";
        given(queueReader.isWaitingToken(token)).willReturn(true);
        given(queueReader.isActiveToken(token)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> tokenValidator.validateToken(token))
                .isInstanceOf(RestApiException.class)
                .hasMessage(WAITING_TOKEN.getMessage());
    }

    @DisplayName("토큰이 유효하지 않으면 예외가 발생한다.")
    @Test
    void validateTokenWithInvalidToken() {
        // given
        String token = "abc123";
        given(queueReader.isWaitingToken(token)).willReturn(false);
        given(queueReader.isNotActiveToken(token)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> tokenValidator.validateToken(token))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_TOKEN.getMessage());
    }

}