package hhplus.concert.domain.payment.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.payment.models.Payment;
import hhplus.concert.domain.payment.repositories.PaymentWriterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentWriterTest {

    @InjectMocks
    PaymentWriter paymentWriter;

    @Mock
    PaymentWriterRepository paymentWriterRepository;

    @DisplayName("인자값이 모두 유효하면, 예약 결제에 성공한다.")
    @Test
    void payBooking_Success_ifWithValidArguments() {
        // given
        Booking booking = Booking.builder().id(1L).build();
        long amount = 10000L;
        Payment expected = Payment.builder()
                .paymentAmount(amount)
                .booking(booking)
                .build();

        given(paymentWriterRepository.save(any(Payment.class))).willReturn(expected);

        // when
        Payment result = paymentWriter.payBooking(booking, amount);

        // then
        assertThat(result.getBooking().getId()).isEqualTo(expected.getBooking().getId());
        assertThat(result.getPaymentAmount()).isEqualTo(expected.getPaymentAmount());
        verify(paymentWriterRepository, times(1)).save(any(Payment.class));
    }
}