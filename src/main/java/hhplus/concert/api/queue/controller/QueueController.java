package hhplus.concert.api.queue.controller;

import hhplus.concert.api.queue.usecase.CreateTokenUseCase;
import hhplus.concert.api.queue.usecase.FindTokenUseCase;
import hhplus.concert.api.queue.dto.response.TokenResponse;
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
    public ResponseEntity<TokenResponse> findToken(@RequestHeader("Queue-Token") String token) {
        return ResponseEntity.ok().body(findTokenUseCase.execute(token));
    }

    @PostMapping
    public ResponseEntity<TokenResponse> createToken() {
        return ResponseEntity.ok().body(createQueueToken.execute());
    }

}
