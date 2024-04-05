package hhplus.concert.api.mock.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequest(@JsonProperty("balance") long balance) {}
