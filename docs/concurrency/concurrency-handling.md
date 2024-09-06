## 동시성 처리 문제

### [낙관적 락]
- 비교적 충돌이 잦지 않을 것이라 예상되는 경우 사용
- 장점
    - 프로세스 처리 간에 불필요한 대기가 발생하지 않는다.
    - 데이터에 Lock을 걸지 않아 자원을 효율적으로 사용할 수 있다.
- 단점
    - 충돌이 발생했을 경우 retry를 하거나 실패처리를 하는 정책이 필요하다.
    - retry를 위한 별도의 작업이 필요해 오버헤드가 발생할 수 있다.
- 구현복잡도
    - 비교적 낮다. 데이터에 버전에 대한 컬럼을 추가하고, 충돌이 발생했을때 사용자에게 충돌정보를 알리거나 재시도하는 로직이 필요하다.
- 성능
    - 비교적 동시성이 높기때문에 작업 응답시간이 높다. 다만 충돌이 빈번하게 발생한다면 별도의 작업으로 오버헤드가 발생할 수 있다.
- 효율성
    - 충돌이 적은 환경에서는 불필요한 Lock을 사용하지 않기 때문에 자원의 활용도에 관한 효율성이 높다.

<br/>

### [비관적 락]
- 충돌이 잦을 것이라 예상되는 경우 사용
- 장점
    - 데이터에 Lock을 걸고 작업이 이뤄지기때문에 데이터 일관성이 보장된다.
- 단점
    - 동시성이 낮아지기 때문에 사용자가 불편함을 느낄 수 있다.
    - 데이터에 Lock을 걸기 때문에 상황에따라 대기시간이 길어질 수 있다.
- 구현복잡도
    - 비교적 높다. Lcok 획득 및 해제하는 로직이 정확하게 구현되어야 하기 때문에 Lock 관리가 복잡할 수 있다
    - DB별 트랜잭션 격리수준에 따라 작동방식이 상이할 수 있기때문에 세심하게 관리 되어야한다.
    - 데드락과 같은 문제도 고려해야한다.
- 성능
    - 비교적 동시성이 낮아 성능면에서는 떨어진다.
    - 충돌발생 시 대기시간이 불가피하다.
    - 다만 충돌이 잦을 것이라 예상되는 경우 데이터의 일관성과 무결성이 보장된다.
- 효율성
    - 자원에 Lock이 걸리기 때문에 비교적 자원의 활용에 관한 효율성이 낮다.

<br/>

***

### [락 적용]
1. 잔액 충전 

   - 비교적 충돌이 잦을 것 같지 않고, '따닥'같은 문제는 사용자의 실수라고 판단하여 두번째 요청은 정책적으로 실패처리
   - JPA 엔티티에 @Version 붙여서 엔티티 변경사항에 대해 버전을 증가시켜 트랜잭션이 커밋되기 직전 데이터 검증
     - User Entity
          ```java
          @Table(name = "users")
          @NoArgsConstructor(access = AccessLevel.PROTECTED)
          @Entity
          public class User {
    
          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          @Column(name = "user_id")
          private Long id;
    
          private String name;
          private long balance;
    
          @Column(name = "versions")
          @Version
          private Long version;
         ...
          ```
     - chargeBalance() 낙관적 락 적용
          ```java
          @RequiredArgsConstructor
          @Service
          public class BalanceService {
        
              private final BalanceHistoryWriter balanceHistoryWriter;
              private final EntityManager em;
        
              public Balance chargeBalanceWithOptimisticLock(Balance balance) {
                  try {
                      chargeBalance(balance);
        
                      balanceHistoryWriter.save(balance);
                      return balance;
                  } catch (OptimisticLockException e) { // 버전 이상 시 충전 실패
                      throw new RestApiException(BalanceErrorCode.FAILED_CHARGE);
                  }
              }
        
              // EntityManager.flush()를 통한 버전 검증
              private void chargeBalance(Balance balance) {
                  long chargeAmount = balance.getAmount();
                  balance.getUser().chargeBalance(chargeAmount);
                  em.flush();
              }
          }
          ```
<br/>

2. 좌석 선점

    - 충돌이 잦을 것으로 예상되며 충돌 발생 시 대기로직 적용
    - 예약을 위한 좌석 엔티티 조회 시 조회 좌석 레코드에 대한 비관적 락 적용 
      - findAllByIds() 비관적 락 적용
         ```java
          public interface SeatJpaRepository extends JpaRepository<Seat, Long> {
            @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적 락 적용
            @Query("select s from Seat s where s.id in :ids")
                List<Seat> findAllByIds(List<Long> ids);
                ...
            }
         ```
        
      - findAllByIds() 비관적 락 적용
         ```java
        @RequiredArgsConstructor
        @UseCase
        public class BookConcertUseCase {
        
            private final SeatReader seatReader;
            ...
        
            @Transactional
            public BookConcertResponse execute(ConcertBookingRequest request) {
                ...
                Booking booking = bookConcert(request);
                ...
                return BookConcertResponse.from(booking);
            }
            ...
        
            private Booking bookConcert(ConcertBookingRequest request) {
                ...
                // 비관적 락 획득 후 좌석 엔티티 반환
                List<Seat> seats = seatReader.getSeatsByIds(request.seatIds());
                return bookingService.book(user, seats);
            }
        }
         ```