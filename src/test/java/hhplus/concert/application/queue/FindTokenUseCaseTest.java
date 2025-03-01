package hhplus.concert.application.queue;

import hhplus.concert.application.queue.usecase.FindTokenUseCase;
import hhplus.concert.core.queue.domain.model.Queue;
import hhplus.concert.representer.api.queue.request.QueueTokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static hhplus.concert.core.queue.domain.model.Key.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FindTokenUseCaseTest {

    @Mock
    QueueService queueService;

    @InjectMocks
    FindTokenUseCase findTokenUseCase;

    @DisplayName("토큰을 조회한다.")
    @Test
    void execute() {
        // given
        Queue queue = Queue.builder()
                .token("a1b2b3")
                .key(ACTIVE)
                .build();
        given(queueService.getQueueByToken(anyString())).willReturn(queue);

        // when
        QueueTokenRequest request = new QueueTokenRequest("a1b2b3");
        Queue result = findTokenUseCase.execute(request);

        // then
        assertThat(result.getToken()).isEqualTo("a1b2b3");
        assertThat(result.getKey()).isEqualTo(ACTIVE);
    }
}