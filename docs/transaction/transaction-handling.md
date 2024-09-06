### 트랜잭션 핸들링

트랜잭션은 가급적 최소한의 범위와 최소한의 책임으로 적용해야한다. 이유는 다음과 같다.

1. 트랜잭션은 관심사를 명확히 해야한다.
2. 만약 한 트랜잭션내에 여러 DB가 물려있어 특정 로직에 Lock이 걸리거나 특정 로직의 수행시간이 길다면 다른 로직들이 기다리는 문제가 있어 성능이 저하된다.
3. 관심사 분리가 되지 않아 OOP의 SRP도 지켜지지 못한다.


콘서트 예약 결제 API의 유즈케이스에서 기존에 여러 관심이 몰려있었고 위와 같은 잠재적인 문제가 있었다. 기존 콘서트 예약 결제 유즈케이스에는 아래와 같은 범위를 한 트랜잭션으로 수행했다.

1. 결제(잔액 차감)
2. 잔액내역 저장
3. 결제내역 저장
4. 예약상태 업데이트
5. 좌석 예약상태 업데이트

총 5가지의 로직을 수행했고 기존의 코드는 아래와 같다.

#### 기존 로직
```java
@UseCase
RequiredArgsConstructor
public class PayBookingUseCase {

	private final PaymentService paymentService;
    
	@Transactional
    public PaymentResponse execute(Long id, PaymentRequest request) {
        Payment payment = createPayment(id, request);
        
        // 결제
        paymentService.pay(payment);
        
        // 잔액 내역 save
        balanceHistoryWriter.save(Balance.createUseBalanceFrom(payment));
        
        // 결제 내역 save
        paymentHistoryWriter.save(payment);
        
        // 예약 상태 update
        payment.getBooking().markAsComplete();
        
        // 좌석 예약상태 update
        payment.getBooking().markSeatsAsBooked();

        return PaymentResponse.from(payment);
    }
}

@Component
public class PaymentService {
    public void pay(Payment payment) {
        // 검증
        validatePay(payment);
        
        // 잔액 차감
        useBalance(payment);
    }
}
```

<br>

결국 문제점들을 해결하기위해 예약 결제 유즈케이스는 **결제만 처리**하고 결제완료 이벤트를 발행한 후 트랜잭션을 커밋한다.  나머지 내역 저장, 예약상태 업데이트 등과 같은 로직들은 예약 결제 유즈케이스에서 발행한 이벤트를 리스닝하여 수행한다.
결제 전 필요한 검증들을 수행한 후 결제를 완료하고 아래의 코드와 같이 결제완료 이벤트를 발행하고 트랜잭션을 종료했다.

#### 변경 후
```java
@UseCase
RequiredArgsConstructor
public class PayBookingUseCase {
    
    private final PaymentService paymentService;
    private final ApplicationEventPublisher eventPublisher;
    
    @Transactional
    public PaymentResponse execute(Long id, PaymentRequest request) {
        Payment payment = createPayment(id, request);
        paymentService.pay(payment);
        eventPublisher.publishEvent(PaymentCompletion.from(payment));
        return PaymentResponse.from(payment);
    }
}


@RequiredArgsConstructor
@Component
public class PaymentCompletionEventListener {
    ...
    private final PaymentHistoryWriter paymentHistoryWriter;
    private final BalanceHistoryWriter balanceHistoryWriter;

    // 잔액 및 결제 내역 저장 로직
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransactionRecords(PaymentCompletion paymentCompletion) {
        Payment payment = createPayment(paymentCompletion.bookingId(), paymentCompletion.payerId());
        balanceHistoryWriter.save(Balance.createUseBalanceFrom(payment));
        paymentHistoryWriter.save(payment);
    }
    
    // 예약 및 좌석 예약상태 변경 로직
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeBooking(PaymentCompletion paymentCompletion) {
        Booking booking = bookingReader.getBookingById(paymentCompletion.bookingId());
        booking.markAsComplete();
        booking.markSeatsAsBooked();
    }
	...
}

```

이렇게 변경된 로직은 예약 결제 유즈케이스에서 이벤트를 발행하고 트랜잭션 커밋 후 이벤트 리스너가 그 다음 작업들을 수행하여 서로의 트랜잭션에 영향을 주지 않고 각 트랜잭션은 최소한의 책임만 지게 된다.
