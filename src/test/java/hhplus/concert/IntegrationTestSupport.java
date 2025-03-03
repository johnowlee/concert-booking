package hhplus.concert;

import hhplus.concert.core.booking.infrastructure.repository.BookingJpaRepository;
import hhplus.concert.core.booking.infrastructure.repository.BookingSeatJpaRepository;
import hhplus.concert.core.concert.infrastructure.repository.ConcertJpaRepository;
import hhplus.concert.core.concert.infrastructure.repository.ConcertOptionJpaRepository;
import hhplus.concert.core.concert.infrastructure.repository.SeatJpaRepository;
import hhplus.concert.core.transaction.infrastructure.repository.TransactionJpaRepository;
import hhplus.concert.core.payment.infrastructure.repository.PaymentJpaRepository;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected RedisTemplate<String, Object> redisTemplate;

    @MockBean
    protected RedisMessageListenerContainer redisContainer;


    @Autowired
    protected BookingSeatJpaRepository bookingSeatJpaRepository;

    @Autowired
    protected SeatJpaRepository seatJpaRepository;

    @Autowired
    protected ConcertOptionJpaRepository concertOptionJpaRepository;

    @Autowired
    protected ConcertJpaRepository concertJpaRepository;


    @Autowired
    protected TransactionJpaRepository transactionJpaRepository;

    @Autowired
    protected PaymentJpaRepository paymentJpaRepository;

    @Autowired
    protected BookingJpaRepository bookingJpaRepository;

    @Autowired
    protected UserJpaRepository userJpaRepository;


    @AfterEach
    void tearDown() {
        bookingSeatJpaRepository.deleteAllInBatch();
        seatJpaRepository.deleteAllInBatch();
        concertOptionJpaRepository.deleteAllInBatch();
        concertJpaRepository.deleteAllInBatch();

        transactionJpaRepository.deleteAllInBatch();
        paymentJpaRepository.deleteAllInBatch();
        bookingJpaRepository.deleteAllInBatch();
        userJpaRepository.deleteAllInBatch();
    }
}
