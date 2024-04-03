package hhplus.concert.api.dto.response.user;

import hhplus.concert.domain.model.enums.QueueStatus;

import java.time.LocalDateTime;

public record QueueResponse(String queueId, long position,
                            LocalDateTime issueDatetime,
                            LocalDateTime expiryDatetime,
                            QueueStatus status) {}
