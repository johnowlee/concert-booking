package hhplus.concert.representer.api.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record PaymentResponse(Long id, LocalDateTime paymentDateTime, Integer paymentAmount) {}