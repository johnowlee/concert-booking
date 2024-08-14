package hhplus.concert.api.queue.controller;

import hhplus.concert.api.queue.dto.request.QueueTokenRequest;
import hhplus.concert.api.queue.usecase.CreateTokenUseCase;
import hhplus.concert.api.queue.usecase.FindTokenUseCase;
import hhplus.concert.api.queue.dto.response.QueueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class QueueController {

    private final FindTokenUseCase findTokenUseCase;
    private final CreateTokenUseCase createQueueToken;

    @GetMapping
    public ResponseEntity<QueueResponse> findToken(@RequestHeader("Queue-Token") QueueTokenRequest request) {
        return ResponseEntity.ok().body(findTokenUseCase.execute(request));
    }

    @PostMapping
    public ResponseEntity<QueueResponse> createToken() {
        return ResponseEntity.ok().body(createQueueToken.execute());
    }

}
