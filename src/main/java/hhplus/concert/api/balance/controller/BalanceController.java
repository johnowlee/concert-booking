package hhplus.concert.api.balance.controller;

import hhplus.concert.api.fakeStore.FakeStore;
import hhplus.concert.api.fakeStore.dto.request.UserRequest;
import hhplus.concert.api.fakeStore.dto.response.user.UserWithBalanceResponse;
import hhplus.concert.api.fakeStore.dto.response.user.chargeBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

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
