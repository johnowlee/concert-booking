package hhplus.concert.representer.api.queue;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.representer.api.queue.request.QueueTokenRequest;
import hhplus.concert.application.queue.CreateTokenUseCase;
import hhplus.concert.application.queue.FindTokenUseCase;
import hhplus.concert.representer.api.queue.response.QueueResponse;
import hhplus.concert.representer.api.common.RestApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class QueueController {

    private final FindTokenUseCase findTokenUseCase;
    private final CreateTokenUseCase createTokenuseCase;
    private final QueueControllerMapper mapper;

    @GetMapping
    public RestApiResponse<QueueResponse> findToken(@RequestHeader("Queue-Token") QueueTokenRequest request) {
        Queue queue = findTokenUseCase.execute(request);
        return RestApiResponse.ok(mapper.toQueueResponse(queue));
    }

    @PostMapping
    public RestApiResponse<QueueResponse> createToken() {
        Queue queue = createTokenuseCase.execute();
        return RestApiResponse.ok(mapper.toQueueResponse(queue));
    }
}
