package hhplus.concert.representer.api.queue.controller;

import hhplus.concert.representer.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.representer.api.queue.usecase.CreateTokenUseCase;
import hhplus.concert.representer.api.queue.usecase.FindTokenUseCase;
import hhplus.concert.representer.api.queue.usecase.response.QueueResponse;
import hhplus.concert.representer.api.common.RestApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class QueueController {

    private final FindTokenUseCase findTokenUseCase;
    private final CreateTokenUseCase createQueueToken;

    @GetMapping
    public RestApiResponse<QueueResponse> findToken(@RequestHeader("Queue-Token") QueueTokenRequest request) {
        return RestApiResponse.ok(findTokenUseCase.execute(request));
    }

    @PostMapping
    public RestApiResponse<QueueResponse> createToken() {
        return RestApiResponse.ok(createQueueToken.execute());
    }
}
