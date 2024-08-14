package hhplus.concert.api.queue.controller;

import hhplus.concert.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.api.queue.usecase.CreateTokenUseCase;
import hhplus.concert.api.queue.usecase.FindTokenUseCase;
import hhplus.concert.api.queue.usecase.response.QueueResponse;
import lombok.RequiredArgsConstructor;
import hhplus.concert.api.common.RestApiResponse;
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
