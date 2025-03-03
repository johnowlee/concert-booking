package hhplus.concert.application.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PayBookingUseCaseTest {


//    @Mock
//    BookingQueryService bookingQueryService;
//
//    @Mock
//    PaymentService paymentService;
//
//    @Mock
//    ClockManager clockManager;
//
//    @Mock
//    UserQueryService userQueryService;
//
//    @Mock
//    ApplicationEventPublisher eventPublisher;
//
//    @InjectMocks
//    PayBookingUseCase payBookingUseCase;
//
//    @DisplayName("예약을 결제하고 결제 완료 이벤트를 발행한다.")
//    @Test
//    void execute() {
//        // given
//        Long bookingId = 1L;
//        Long userId = 2L;
//
//        Booking booking = mock(Booking.class);
//        User payer = mock(User.class);
//        LocalDateTime paymentDateTime = LocalDateTime.now();
//
//        given(bookingQueryService.getBookingById(bookingId)).willReturn(booking);
//        given(userQueryService.getUserById(userId)).willReturn(payer);
//        given(clockManager.getNowDateTime()).willReturn(paymentDateTime);
//
//        // when
//        payBookingUseCase.execute(userId, bookingId);
//
//        // then
//        then(bookingQueryService).should(times(1)).getBookingById(bookingId);
//        then(userQueryService).should(times(1)).getUserById(userId);
//        then(clockManager).should(times(1)).getNowDateTime();
//        then(paymentService).should(times(1)).pay(any(Payment.class));
//        then(eventPublisher).should(times(1)).publishEvent(any(PaymentCompletion.class));
//    }
}