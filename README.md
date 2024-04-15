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
	|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; bookingStatus|String|예약 상태<br>(COMPLETE, UNCOMPLETE)|
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
	|&nbsp;&nbsp;&nbsp;&nbsp; bookingStatus|String|예약 상태<br>(COMPLETE, UNCOMPLETE)|
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





