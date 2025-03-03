package hhplus.concert.representer.api.user.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(@NotBlank String name) {
}
