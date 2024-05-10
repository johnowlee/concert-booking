> # Sequence Diagram

![sequence-diagram](https://github.com/WonhoLeeDev/hhplus-concert/blob/main/sequnce-diagram.png)
***********************************
> # API명세

# 1. 대기열 토큰
## 1-1. 토큰 조회
### REQUEST
 - #### Method
  |Method|URL|
  |---|---|
  |GET|/queue/{userId}|
  
 - #### Header
  |Parameter|Description|
  |---|---|
  |Content-Type|<span style="color:red">application/json</span>|

 - #### Path Parameter
  |Name|Type|Description|Required|
  |---|---|---|---|
  |userId|int|유저의 ID|필수|
  <br>

### RESPONSE
  - |Name|Type|Description|
  	|---|---|---|
  	|queueId|Sting|대기열 토큰 ID|
  	|position|int|대기 번호|


***********************************

# 2. 콘서트
## 2-1. 콘서트 목록 조회
### REQUEST
  - #### Method
  	|Method|URL|
	|---|---|
	|GET|/concerts|

  - #### Header
  	|Parameter|Description|
	|---|---|
	|Content-Type|<span style="color:red">application/json</span>|
<br>

### RESPONSE
  - |Name|Type|Description|
	|---|---|---|
	|conerts|Object[]|콘서트 정보|
	|&nbsp;&nbsp;&nbsp;&nbsp; concertId|int|콘서트 ID|
	|&nbsp;&nbsp;&nbsp;&nbsp; title|String|콘서트명|
	|&nbsp;&nbsp;&nbsp;&nbsp; concertDateTime|String|콘서트 일시|
	|&nbsp;&nbsp;&nbsp;&nbsp; availableSeatsCount|int|잔여좌석 수|
    <br>

## 2-2. 콘서트 상세 조회(좌석)
### REQUEST
  - #### Method
  	|Method|URL|
	|---|---|
	|GET|/concerts/{concertId}|

  - #### Header
  	|Parameter|Description|
	|---|---|
	|Content-Type|<span style="color:red">application/json</span>|
    
    
  - #### Path Parameter
 	|Name|Type|Description|Required|
	|---|---|---|---|
	|concertId|int|콘서트 ID|필수|
	<br>

### RESPONSE
  - |Name|Type|Description|
	|---|---|---|
	|concertId|int|콘서트 ID|
	|title|String|콘서트명|
	|concertDateTime|String|콘서트 일시|
	|seats|Object[]|좌석정보|
	|&nbsp;&nbsp;&nbsp;&nbsp; seatId|String|좌석 ID|
	|&nbsp;&nbsp;&nbsp;&nbsp; seatNo|String|좌석 번호|
	|&nbsp;&nbsp;&nbsp;&nbsp; status|String|좌석 예약 상태|
	<br>

## 2-3. 콘서트 예약
### REQUEST
  - #### Method
  	|Method|URL|
	|---|---|
	|POST|/concerts/{concertId}/booking|

  - #### Header
  	|Parameter|Description|
	|---|---|
	|Queue-Token|대기열 Token값|
	|Content-Type|<span style="color:red">application/json</span>|
        
  - #### Path Parameter
  	|Name|Type|Description|Required|
	|---|---|---|---|
	|concertId|int|콘서트 ID|필수|

  - #### Body
  	|Name|Type|Description|Required|
	|---|---|---|---|
	|seat_no|String|좌석번호<br>하나 이상 요청<br>둘 이상 시 쉼표로 구분<br>	 예) <span style="color:red">10,11</span> |필수|
	<br>

### RESPONSE
  - |Name|Type|Description|
	|---|---|---|
	|bookingResult|Sting|예약 결과<br>(SUCCESS, FAILURE)|
	|bookingDateTime|String|예약 일시|
	|bookingUserName|String|예약자명|
	|concert|Object|콘서트 정보|
	|&nbsp;&nbsp;&nbsp;&nbsp; title|String|콘서트명|
	|&nbsp;&nbsp;&nbsp;&nbsp; concertDateTime|String|콘서트 일시|
	|&nbsp;&nbsp;&nbsp;&nbsp; seats|String[]|좌석번호|
	

***********************************

# 3. 예약

## 3-1. 예약 목록 조회
### REQUEST
  - #### Method
  	|Method|URL|
	|---|---|
	|GET|/bookings/users/{userId}|

  - #### Header
  	|Parameter|Description|
	|---|---|
	|Content-Type|<span style="color:red">application/json</span>|
    
  - #### Path Parameter
  	|Name|Type|Description|Required|
	|---|---|---|---|
	|userId|int|유저의 ID|필수|
	<br>

### RESPONSE
  - |Name|Type|Description|
	|---|---|---|
	|bookings|Object[]|예약 목록 정보|
    |&nbsp;&nbsp;&nbsp;&nbsp; booking|Object|예약 목록|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; bookingId|int|예약 ID|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; seatBookingStatus|String|예약 상태<br>(COMPLETE, UNCOMPLETE)|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; bookingDateTime|String|예약 일시|
    |&nbsp;&nbsp;&nbsp;&nbsp; bookingConcert|Object|콘서트 정보|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; title|String|콘서트명|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; concertDateTime|String|콘서트 일시|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; seats|String[]|좌석정보|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; seatNo|String|좌석번호|
	<br>

## 3-2. 예약 상세 조회
### REQUEST
  - #### Method
  	|Method|URL|
	|---|---|
	|GET|/bookings/{bookingId}|

  - #### Header
  	|Parameter|Description|
	|---|---|
	|Content-Type|<span style="color:red">application/json</span>|
    
    
  - #### Path Parameter
  	|Name|Type|Description|Required|
	|---|---|---|---|
    |bookingId|int|예약 ID|필수|
	
	<br>

### RESPONSE
  - |Name|Type|Description|
	|---|---|---|
    |booking|Object|예약 정보|
	|&nbsp;&nbsp;&nbsp;&nbsp; bookingId|int|예약 ID|
	|&nbsp;&nbsp;&nbsp;&nbsp; seatBookingStatus|String|예약 상태<br>(COMPLETE, UNCOMPLETE)|
	|&nbsp;&nbsp;&nbsp;&nbsp; bookingDateTime|String|예약 일시|
	|bookingUser|Object|예약자 정보|
	|&nbsp;&nbsp;&nbsp;&nbsp; userId|int|예약자 ID|
	|&nbsp;&nbsp;&nbsp;&nbsp; name|String|예약자명|
	|bookingConcert|Object|콘서트 정보|
	|&nbsp;&nbsp;&nbsp;&nbsp; concertId|int|콘서트 ID|
	|&nbsp;&nbsp;&nbsp;&nbsp; title|String|콘서트명|
	|&nbsp;&nbsp;&nbsp;&nbsp; concertDateTime|String|콘서트 일시|
	|&nbsp;&nbsp;&nbsp;&nbsp; seats|Object[]|좌석정보|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; seatId|int|좌석 ID|
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; seatNo|String|좌석 번호|
<br>

## 3-3. 예약 결제
### REQUEST
  - #### Method
  	|Method|URL|
	|---|---|
	|GET|/bookings/{bookingId}/payment|

  - #### Header
  	|Parameter|Description|
	|---|---|
	|Queue-Token|대기열 Token값|
	|Content-Type|<span style="color:red">application/json</span>|
    
  - #### Path Parameter
  	|Name|Type|Description|Required|
	|---|---|---|---|
	|bookingId|int|예약 ID|필수|
	<br>

### RESPONSE
  - |Name|Type|Description|
	|---|---|---|
	|paymentResult|Sting|결제 결과</br>(SUCCESS, FAILURE)|

***********************************

# 4. 잔액
## 4-1. 잔액 조회
### REQUEST
  - #### Method
  	|Method|URL|
	|---|---|
	|GET|/balance/{userId}|
	
  - #### Header
  	|Parameter|Description|
	|---|---|
	|Content-Type|<span style="color:red">application/json</span>|
    
  - #### Path Parameter
  	|Name|Type|Description|Required|
	|---|---|---|---|
	|userId|int|유저의 ID|필수|
	<br>

### RESPONSE
  - |Name|Type|Description|
	|---|---|---|
	|balance|int|잔액|
	<br>

## 4-2. 잔액 충전
### REQUEST
  - #### Method
  	|Method|URL|
	|---|---|
	|POST|/balance/{userId}|
	
  - #### Header
	|Parameter|Description|
	|---|---|
	|Content-Type|<span style="color:red">application/json</span>|
    
  - #### Path Parameter
	|Name|Type|Description|Required|
	|---|---|---|---|
	|userId|int|유저 ID|필수|
    
  - #### Body
	|Name|Type|Description|Required|
	|---|---|---|---|
	|balance|int|충전금액|필수|
	<br>

### RESPONSE
  - |Name|Type|Description|
	|---|---|---|
	|chargeResult|Sting|충전결과<br>(SUCCESS, FAILURE)|
	|balance|int|잔액|




***********************************

> # ERD

![ERD](https://github.com/WonhoLeeDev/hhplus-concert/blob/main/ERD.png)
***********************************

> # Milestone

https://github.com/WonhoLeeDev/hhplus-concert/milestones?with_issues=no

***********************************

> # API Swagger UI

![API Swagger UI](https://github.com/WonhoLeeDev/hhplus-concert/blob/main/API%20Swagger%20UI.png)

***********************************

### 브랜치 전략

`main`
- 배포 운영환경 branch

`stage`
- QA환경 branch
- QA 테스트 수행
- 개발환경 branch에서 완료되면 병합

`develop`
- 개발환경 branch
- 개발 feature별로 분기 가능

***********************************
### 동시성 처리 문제
1. 잔액충전
	- 낙관적락 적용 : 비교적 충돌이 잦을 것 같지 않고, '따닥'같은 문제는 사용자의 실수라고 판단하여 두번째 요청은 정책적으로 실패처리 
2. 좌석선점
	- 비관적락 적용 : 충돌이 잦을 것으로 예상되며 충돌 발생 시 대기로직 적용

#### 낙관적 락
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


#### 비관적 락
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

***********************************
### 인덱스 적용
https://velog.io/@wonholee/DB-%EC%BD%98%EC%84%9C%ED%8A%B8-%EC%98%88%EC%95%BD-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9D%B8%EB%8D%B1%EC%8A%A4-%EB%8F%84%EC%9E%85%EA%B8%B0
***********************************
### 대기열 구현 - Redis
https://velog.io/@wonholee/Redis-%EC%BD%98%EC%84%9C%ED%8A%B8-%EC%98%88%EC%95%BD-%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%8C%80%EA%B8%B0%EC%97%B4%EA%B5%AC%ED%98%84
