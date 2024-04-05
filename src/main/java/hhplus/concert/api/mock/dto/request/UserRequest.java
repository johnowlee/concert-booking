package hhplus.concert.api.mock.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequest(@JsonProperty("user_id") long userId,
                          @JsonProperty("balance") long balance) {}
