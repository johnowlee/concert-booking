package hhplus.concert.api.dto.response.user;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(long userId, String name, long balance) {}
