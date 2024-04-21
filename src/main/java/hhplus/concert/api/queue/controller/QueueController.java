package hhplus.concert.api.queue.controller;

import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.api.queue.usecase.GetQueueByUserIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/queue")
public class QueueController {

    private final GetQueueByUserIdUseCase getQueueByUserIdUseCase;

    @GetMapping("/users/{userId}")
    public QueueResponse queue(@PathVariable Long userId) {
        return getQueueByUserIdUseCase.excute(userId);
    }

}
