package hhplus.concert.core.history.balance.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

//    @Mock
//    private TransactionCommandService transactionCommandService;
//
//    @Mock
//    private EntityManager em;
//
//    @InjectMocks
//    private TransactionService transactionService;
//
//    @Test
//    void testChargeBalanceWithOptimisticLock_success() {
//        // given
//        User user = mock(User.class);
//        Transaction transaction = mock(Transaction.class);
//
//        given(transaction.getUser()).willReturn(user);
//
//        // when
//        transactionService.chargeBalanceWithOptimisticLock(transaction);
//
//        // then
//        then(transactionCommandService).should(times(1)).save(transaction);
//    }
//
//    @Test
//    void testChargeBalanceWithOptimisticLock_optimisticLockException() {
//        // given
//        User user = mock(User.class);
//        Transaction transaction = mock(Transaction.class);
//
//        given(transaction.getUser()).willReturn(user);
//        doThrow(OptimisticLockException.class).when(em).flush();
//
//        // when & then
//        Assertions.assertThatThrownBy(() -> transactionService.chargeBalanceWithOptimisticLock(transaction))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(BalanceErrorCode.FAILED_CHARGE.getMessage());
//    }
}