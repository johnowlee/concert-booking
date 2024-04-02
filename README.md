> # Sequence Diagram

![sequence-diagram](https://github.com/WonhoLeeDev/hhplus-concert/blob/main/sequence-diagram.png)
***********************************
> # API명세

# 1. 토큰
## 1-1. 토큰 조회
### REQUEST
#### 호출방식
|Method|URL|
|---|---|
|GET|/token/{userId}|
#### 요청 헤더
|Parameter|Description|
|---|---|
|Content-Type|<span style="color:red">application/json</span>|
    
    
#### 요청 파라미터
|Name|Type|Description|Required|
|---|---|---|---|
|userId|int|유저의 ID|필수|

### RESPONSE
#### 응답 객체
|Name|Type|Description|
|---|---|---|
|token_id|Sting|토큰 ID|

***********************************

# 2. 예약
## 2-1. 콘서트 조회
### REQUEST
#### 호출방식
|Method|URL|
|---|---|
|GET|/concerts|

#### 요청 헤더
|Parameter|Description|
|---|---|
|Content-Type|<span style="color:red">application/json</span>|
    
    
#### 요청 파라미터
|Name|Type|Description|Required|
|---|---|---|---|
|-|-|-|-|

### RESPONSE
#### 응답 객체
|Name|Type|Description|
|---|---|---|
|concerts|Object[]|콘서트 목록|
|&nbsp;&nbsp;&nbsp;&nbsp; ceoncert_id|int|콘서트 ID|
|&nbsp;&nbsp;&nbsp;&nbsp; ceoncert_dateTime|int|콘서트 날짜 시간|
|&nbsp;&nbsp;&nbsp;&nbsp; seats|Object[]|좌석|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; seat_id|String|좌석 ID|
|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; seat_status|String|좌석 예약 상태|

## 2-2. 콘서트 예약
### REQUEST
#### 호출방식
|Method|URL|
|---|---|
|POST|/booking|

#### 요청 헤더
|Parameter|Description|
|---|---|
|Token-Auth|Token값|
|Content-Type|<span style="color:red">application/json</span>|
    
    
#### 요청 파라미터
|Name|Type|Description|Required|
|---|---|---|---|
|user_id|int|유저의 ID|필수|
|concert_id|int|콘서트 ID|필수|
|seat_no|String|좌석번호<br>하나 이상 요청<br>둘 이상 시 쉼표로 구분<br> 예) <span style="color:red">10,11</span> |필수|

### RESPONSE
#### 응답 객체
|Name|Type|Description|
|---|---|---|
|booking_result|Sting|예약 결과|

***********************************

# 3. 결제
## 3-1. 결제
### REQUEST
#### 호출방식
|Method|URL|
|---|---|
|POST|/payment|

#### 요청 헤더
|Parameter|Description|
|---|---|
|Token-Auth|Token값|
|Content-Type|<span style="color:red">application/json</span>|
    
    
#### 요청 파라미터
|Name|Type|Description|Required|
|---|---|---|---|
|user_id|int|유저의 ID|필수|
|booking_id|int|예약 ID|필수|

### RESPONSE
#### 응답 객체
|Name|Type|Description|
|---|---|---|
|payment_result|Sting|결제 결과|

***********************************

# 4. 잔액
## 4-1. 잔액 조회
### REQUEST
#### 호출방식
|Method|URL|
|---|---|
|GET|/balance/{userId}|

#### 요청 헤더
|Parameter|Description|
|---|---|
|Content-Type|<span style="color:red">application/json</span>|
    
    
#### 요청 파라미터
|Name|Type|Description|Required|
|---|---|---|---|
|userId|int|유저의 ID|필수|

### RESPONSE
#### 응답 객체
|Name|Type|Description|
|---|---|---|
|user_id|Sting|유저 ID|
|balance|int|잔액|

## 4-2. 잔액 충전
### REQUEST
#### 호출방식
|Method|URL|
|---|---|
|POST|/balance|

#### 요청 헤더
|Parameter|Description|
|---|---|
|Content-Type|<span style="color:red">application/json</span>|
    
    
#### 요청 파라미터
|Name|Type|Description|Required|
|---|---|---|---|
|user_id|int|유저 ID|필수|
|balance|int|충전금액|필수|


### RESPONSE
#### 응답 객체
|Name|Type|Description|
|---|---|---|
|user_id|Sting|유저 ID|
|balance|int|잔액|



