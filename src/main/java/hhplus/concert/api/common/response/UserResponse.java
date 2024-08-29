package hhplus.concert.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.user.models.User;
import lombok.Builder;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@JsonInclude(NON_NULL)
public record UserResponse(
        Long id,
        String name,
        Long balance,
        List<BookingResponse> bookings,
        List<PaymentResponse> payments
) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getBalance(), null, null);
    }
}
