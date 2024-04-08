package hhplus.concert.api.fakeStore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequest(@JsonProperty("balance") long balance) {}
