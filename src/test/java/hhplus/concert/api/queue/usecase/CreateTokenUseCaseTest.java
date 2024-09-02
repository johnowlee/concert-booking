package hhplus.concert.api.queue.usecase;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.api.queue.usecase.response.QueueResponse;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.service.QueueService;
import hhplus.concert.domain.queue.support.factory.score.ScoreFactory;
import hhplus.concert.domain.queue.support.factory.token.TokenFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static hhplus.concert.domain.queue.model.Key.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


class CreateTokenUseCaseTest extends IntegrationTestSupport {

    @Autowired
    CreateTokenUseCase createTokenUseCase;

    @MockBean
    QueueService queueService;

    @MockBean
    TokenFactory tokenFactory;

    @MockBean
    ScoreFactory scoreFactory;

    @DisplayName("토큰을 생성한다.")
    @Test
    void execute() {
        // given
        String token = "abc123";
        long score = 12345L;

        Queue queue = Queue.builder()
                .token(token)
                .key(ACTIVE)
                .build();

        given(tokenFactory.generateToken()).willReturn(token);
        given(scoreFactory.getTimeScore()).willReturn(score);
        given(queueService.createNewQueue(token, score)).willReturn(queue);

        // when
        QueueResponse result = createTokenUseCase.execute();

        // then
        assertThat(result.token()).isEqualTo("abc123");
        assertThat(result.key()).isEqualTo(ACTIVE.getKeyName());
    }
}