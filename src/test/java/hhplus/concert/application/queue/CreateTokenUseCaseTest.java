package hhplus.concert.application.queue;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.application.queue.usecase.CreateTokenUseCase;
import hhplus.concert.core.queue.domain.model.Queue;
import hhplus.concert.core.queue.domain.service.support.factory.score.ScoreFactory;
import hhplus.concert.core.queue.domain.service.support.factory.token.TokenFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static hhplus.concert.core.queue.domain.model.Key.ACTIVE;
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
        Queue result = createTokenUseCase.execute();

        // then
        assertThat(result.getToken()).isEqualTo("abc123");
        assertThat(result.getKey()).isEqualTo(ACTIVE);
    }
}