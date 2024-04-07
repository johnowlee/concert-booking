package hhplus.concert.api.user.controller;

import hhplus.concert.api.mock.FakeStore;
import hhplus.concert.api.mock.dto.request.UserRequest;
import hhplus.concert.api.mock.dto.response.user.UserWithBalanceResponse;
import hhplus.concert.api.mock.dto.response.user.chargeBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class MockQueueController {

    private final FakeStore fakeStore;

    @GetMapping("{id}")
    public UserWithBalanceResponse balance(@PathVariable long id) {
        return fakeStore.getBalance(id);
    }

    @PostMapping("{id}")
    public chargeBalanceResponse booking(@PathVariable long id, @RequestBody UserRequest userRequest) {
        return fakeStore.chargeBalance(id, userRequest);
    }
}
