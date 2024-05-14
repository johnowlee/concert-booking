package hhplus.concert.api.queue.controller;

import hhplus.concert.api.queue.usecase.RedisQueueService;
import hhplus.concert.api.queue.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class QueueController {

    private final RedisQueueService redisQueueService;

    @GetMapping
    public TokenResponse findToken(@RequestHeader("Queue-Token") String token) {
        return redisQueueService.findToken(token);
    }

    @PostMapping
    public ResponseEntity<TokenResponse> createToken() {
        return ResponseEntity.ok().body(redisQueueService.createQueueToken());
    }

}
