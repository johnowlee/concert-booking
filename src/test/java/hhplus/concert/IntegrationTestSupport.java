package hhplus.concert;

import hhplus.concert.domain.booking.infrastructure.BookingJpaRepository;
import hhplus.concert.domain.booking.infrastructure.BookingSeatJpaRepository;
import hhplus.concert.domain.concert.infrastructure.ConcertJpaRepository;
import hhplus.concert.domain.concert.infrastructure.ConcertOptionJpaRepository;
import hhplus.concert.domain.concert.infrastructure.SeatJpaRepository;
import hhplus.concert.domain.history.balance.infrastructure.BalanceJpaRepository;
import hhplus.concert.domain.history.payment.infrastructure.PaymentJpaRepository;
import hhplus.concert.domain.user.infrastructure.UserJpaRepository;
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
    protected BalanceJpaRepository balanceJpaRepository;

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

        balanceJpaRepository.deleteAllInBatch();
        paymentJpaRepository.deleteAllInBatch();
        bookingJpaRepository.deleteAllInBatch();
        userJpaRepository.deleteAllInBatch();
    }
}
