# API명세

---

# 1. 대기열 토큰
## 1-1. 토큰 생성
### REQUEST
 - #### Method
  | Method | URL    |
  |--------|--------|
  | POST   | /queue |
  
 - #### Header
  | Parameter    | Description                                     |
  |--------------|-------------------------------------------------|
  | Content-Type | <span style="color:red">application/json</span> |
  
### RESPONSE
  - | Name                       | Type   | Description                        |
  	|----------------------------|--------|------------------------------------|
    | code                       | Int    | 응답 코드                              |
    | status                     | String | 응답 상태                              |
    | message                    | String | 응답 메시지                             |
    | data                       | Object | 응답 데이터                             |
    | &nbsp;&nbsp; token         | Sting  | 대기열 토큰 ID                          |
    | &nbsp;&nbsp; key           | String | 대기열 Key<br>('ACTIVE' or 'WAITING') |
    | &nbsp;&nbsp; waitingNumber | Int    | 대기 번호<br>(key 값이 'WAITING' 시 제공)   |
    <br>


  
## 1-2. 토큰 조회
### REQUEST
 - #### Method
  | Method | URL    |
  |--------|--------|
  | GET    | /queue |
  
 - #### Header
  | Parameter    | Description                                     |
  |--------------|-------------------------------------------------|
  | Queue-Token  | 대기열 Token값                                      |
  | Content-Type | <span style="color:red">application/json</span> |
  
### RESPONSE
  - | Name                       | Type   | Description                        |
  	|----------------------------|--------|------------------------------------|
    | code                       | Int    | 응답 코드                              |
    | status                     | String | 응답 상태                              |
    | message                    | String | 응답 메시지                             |
    | data                       | Object | 응답 데이터                             |
    | &nbsp;&nbsp; token         | Sting  | 대기열 토큰 ID                          |
    |  &nbsp;&nbsp; key          | String | 대기열 Key<br>('ACTIVE' or 'WAITING') |
    | &nbsp;&nbsp; waitingNumber | Int    | 대기 번호<br>(key 값이 'WAITING' 시 제공)   |

***********************************

# 2. 콘서트
## 2-1. 콘서트 목록 조회
### REQUEST
  - #### Method
  	| Method | URL       |
	|--------|-----------|
    |   GET  | /concerts |

    - #### Header
    | Parameter    | Description                                     |
    |--------------|-------------------------------------------------|
    | Content-Type | <span style="color:red">application/json</span> |

### RESPONSE
  - | Name                               | Type     | Description |
	|------------------------------------|----------|-------------|
    | code                               | Int      | 응답 코드       |
    | status                             | String   | 응답 상태       |
    | message                            | String   | 응답 메시지      |
    | data                               | Object   | 응답 데이터      |
    | &nbsp;&nbsp; concerts              | Object[] | 콘서트 정보      |
    | &nbsp;&nbsp;&nbsp;&nbsp; id        | Int      | 콘서트 ID      |
    | &nbsp;&nbsp;&nbsp;&nbsp; title     | String   | 콘서트 명       |
    | &nbsp;&nbsp;&nbsp;&nbsp; organizer | String   | 주최자         |
    
<br>

## 2-2. 콘서트 옵션 목록 조회
### REQUEST
  - #### Method
  	| Method | URL                           |
	|--------|-------------------------------|
    | GET    | /concerts/{concertId}/options |

  - #### Header
    | Parameter    | Description                                     |
    |--------------|-------------------------------------------------|
    | Content-Type | <span style="color:red">application/json</span> |
    
- #### Path Parameter
    | Name      | Type | Description | Required |
    |-----------|------|-------------|----------|
    | concertId | Int  | 콘서트 ID      | 필수       |
	

### RESPONSE
  - | Name                                     | Type     | Description |
	|------------------------------------------|----------|-------------|
    | code                                     | Int      | 응답 코드       |
    | status                                   | String   | 응답 상태       |
    | message                                  | String   | 응답 메시지      |
    | data                                     | Object   | 응답 데이터      |
    | &nbsp;&nbsp;concertOptions               | Object[] | 콘서트 정보      |
    | &nbsp;&nbsp;&nbsp;&nbsp; id              | Int      | 콘서트옵션 ID    |
    | &nbsp;&nbsp;&nbsp;&nbsp; concertDateTime | String   | 콘서트 일시      |
    | &nbsp;&nbsp;&nbsp;&nbsp; place           | String   | 콘서트 장소      |

<br>

## 2-3. 콘서트 옵션 상세 조회(좌석)
### REQUEST
  - #### Method
  	| Method | URL                                 |
	|--------|-------------------------------------|
    | GET    | /concerts/options/{concertOptionId} |

  - #### Header
    | Parameter   | Description                                     |
    |-------------|-------------------------------------------------|
    | Cntent-Type | <span style="color:red">application/json</span> |
    
  - #### Path Parameter
    | Name            | Type | Description | Required |
    |-----------------|------|-------------|----------|
    | concertOptionId | Int  | 콘서트옵션 ID    | 필수       |

### RESPONSE
  - | Name                                       | Type     | Description |
	|--------------------------------------------|----------|-------------|
    | code                                       | Int      | 응답 코드       |
    | status                                     | String   | 응답 상태       |
    | message                                    | String   | 응답 메시지      |
    | data                                       | Object   | 응답 데이터      |
    | &nbsp;&nbsp; concertOption                 | Object   | 콘서트옵션       |
    | &nbsp;&nbsp;&nbsp;&nbsp; id                | Int      | 콘서트옵션 ID    |
    | &nbsp;&nbsp;&nbsp;&nbsp; concertDateTime   | String   | 콘서트 일시      |
    | &nbsp;&nbsp;&nbsp;&nbsp; place             | String   | 콘서트 장소      |
    | &nbsp;&nbsp; seats                         | Object[] | 좌석 정보       |
    | &nbsp;&nbsp;&nbsp;&nbsp; id                | Int      | 좌석 ID       |
    | &nbsp;&nbsp;&nbsp;&nbsp; seatNo            | String   | 좌석 번호       |
    | &nbsp;&nbsp;&nbsp;&nbsp; seatBookingStatus | String   | 좌석 예약 상태    |
    | &nbsp;&nbsp;&nbsp;&nbsp; grade             | String   | 좌석 등급       |
	<br>

## 2-4. 콘서트 예약
### REQUEST
  - #### Method
  	| Method | URL                       |
	|--------|---------------------------|
    | POST   | /concerts/options/booking |

    - #### Header
    | Parameter    | Description                                     |
    |--------------|-------------------------------------------------|
    | Queue-Token  | 대기열 Token값                                      |
    | Content-Type | <span style="color:red">application/json</span> |

    - #### Body
    | Name    | Type   | Description                                                                     | Required |
    |---------|--------|---------------------------------------------------------------------------------|----------|
    | user_id | Int    | 유저 ID                                                                           | 필수       |
    | Seat_id | String | 좌석 ID<br>하나 이상 요청<br>둘 이상 시 쉼표로 구분<br>	 예) <span style="color:red">10,11</span> | 필수       |
	

### RESPONSE
  - | Name                         | Type     | Description |
	|------------------------------|----------|-------------|
    | code                         | Int      | 응답 코드       |
    | status                       | String   | 응답 상태       |
    | message                      | String   | 응답 메시지      |
    | data                         | Object   | 응답 데이터      |
    | &nbsp;&nbsp; bookingId       | Int      | 예약 ID       |
    | &nbsp;&nbsp; bookingDateTime | String   | 예약 일시       |
    | &nbsp;&nbsp; bookerName      | String   | 예약자 명       |
    | &nbsp;&nbsp; concertTitle    | String   | 콘서트 명       |
    | &nbsp;&nbsp; concertDateTime | String   | 콘서트 일시      |
    | &nbsp;&nbsp; seats           | String[] | 좌석 번호 목록    |
	

***********************************

# 3. 예약

## 3-1. 예약 목록 조회
### REQUEST
  - #### Method
  	| Method | URL                      |
	|--------|--------------------------|
    | GET    | /bookings/users/{userId} |

    - #### Header
    | Parameter    | Description                                     |
    |--------------|-------------------------------------------------|
    | Content-Type | <span style="color:red">application/json</span> |
    
    - #### Path Parameter
    | Name   | Type | Description | Required |
    |--------|------|-------------|----------|
    | userId | Int  | 유저 ID       | 필수       |

### RESPONSE
  - | Name                                                 | Type     | Description                     |
	|------------------------------------------------------|----------|---------------------------------|
    | code                                                 | Int      | 응답 코드                           |
    | status                                               | String   | 응답 상태                           |
    | message                                              | String   | 응답 메시지                          |
    | data                                                 | Object   | 응답 데이터                          |
    | &nbsp;&nbsp; bookings                                | Object[] | 예약 목록                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; id                          | Int      | 예약 ID                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; bookingStatus               | String   | 예약 상태<br>(COMPLETE, UNCOMPLETE) |
    | &nbsp;&nbsp;&nbsp;&nbsp; bookingDateTime             | String   | 예약 일시                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; concertTitle                | String   | 콘서트 명                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; booker                      | Object   | 예약자                             |
    | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; id              | Int      | 예약자 ID                          |
    | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; name            | String   | 예약자 명                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; concertOption               | Object   | 콘서트옵션                           |
    | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; id              | Int      | 콘서트옵션 ID                        |
    | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; concertDateTime | String   | 콘서트 일시                          |
    | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; place           | String   | 콘서트 장소                          |
    | &nbsp;&nbsp;&nbsp;&nbsp; seats                       | Object[] | 좌석 정보                           |
    | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; id              | Int      | 좌석 ID                           |
    | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; seatNo          | String   | 좌석 번호                           |
    | &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; grade           | String   | 좌석 등급                           |
	<br>

## 3-2. 예약 상세 조회
### REQUEST
  - #### Method
  	| Method | URL                   |
	|--------|-----------------------|
    | GET    | /bookings/{bookingId} |

  - #### Header
  	| Parameter    | Description                                     |
	|--------------|-------------------------------------------------|
    | Content-Type | <span style="color:red">application/json</span> |
    
  - #### Path Parameter
  	| Name      | Type | Description | Required |
	|-----------|------|-------------|----------|
    | bookingId | int  | 예약 ID       | 필수       |

### RESPONSE
  - | Name                                     | Type     | Description                     |
	|------------------------------------------|----------|---------------------------------|
    | code                                     | Int      | 응답 코드                           |
    | status                                   | String   | 응답 상태                           |
    | message                                  | String   | 응답 메시지                          |
    | data                                     | Object   | 응답 데이터                          |
    | &nbsp;&nbsp; id                          | Int      | 예약 ID                           |
    | &nbsp;&nbsp; bookingStatus               | String   | 예약 상태<br>(COMPLETE, UNCOMPLETE) |
    | &nbsp;&nbsp; bookingDateTime             | String   | 예약 일시                           |
    | &nbsp;&nbsp; concertTitle                | String   | 콘서트 명                           |
    | &nbsp;&nbsp; booker                      | Object   | 예약자                             |
    | &nbsp;&nbsp;&nbsp;&nbsp; id              | Int      | 예약자 ID                          |
    | &nbsp;&nbsp;&nbsp;&nbsp; name            | String   | 예약자 명                           |
    | &nbsp;&nbsp; concertOption               | Object   | 콘서트옵션                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; id              | Int      | 콘서트옵션 ID                        |
    | &nbsp;&nbsp;&nbsp;&nbsp; concertDateTime | String   | 콘서트 일시                          |
    | &nbsp;&nbsp;&nbsp;&nbsp; place           | String   | 콘서트 장소                          |
    | &nbsp;&nbsp; seats                       | Object[] | 좌석 정보                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; id              | Int      | 좌석 ID                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; seatNo          | String   | 좌석 번호                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; grade           | String   | 좌석 등급                           |
<br>

## 3-3. 예약 결제
### REQUEST
  - #### Method
  	| Method | URL                            |
	|--------|--------------------------------|
    | POST   | /bookings/{bookingId}/payments |

    - #### Header
    | Parameter    | Description                                     |
    |--------------|-------------------------------------------------|
    | Queue-Token  | 대기열 Token값                                      |
    | Content-Type | <span style="color:red">application/json</span> |
    
    - #### Path Parameter
    | Name      | Type | Description | Required |
    |-----------|------|-------------|----------|
    | bookingId | Int  | 예약 ID       | 필수       |
    
  - #### Body
  	| Name    | Type | Description | Required |
	|---------|------|-------------|----------|
    | user_id | Int  | 유저 ID       | 필수       |
    
### RESPONSE
  - | Name                                   | Type   | Description                     |
	|----------------------------------------|--------|---------------------------------|
    | code                                   | Int    | 응답 코드                           |
    | status                                 | String | 응답 상태                           |
    | message                                | String | 응답 메시지                          |
    | data                                   | Object | 응답 데이터                          |
    | &nbsp;&nbsp; id                        | Int    | 결제 ID                           |
    | &nbsp;&nbsp; paymentDateTime           | String | 결제 일자                           |
    | &nbsp;&nbsp; paymentAmount             | String | 결제 금액                           |
    | &nbsp;&nbsp; user                      | Object | 결제자 정보                          |
    | &nbsp;&nbsp;&nbsp;&nbsp; id            | Int    | 결제자 ID                          |
    | &nbsp;&nbsp;&nbsp;&nbsp; name          | String | 결제자 명                           |
    | &nbsp;&nbsp; booking                   | Object | 예약 정보                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; id            | Int    | 예약 ID                           |
    | &nbsp;&nbsp;&nbsp;&nbsp; bookingStatus | String | 예약 상태<br>(COMPLETE, UNCOMPLETE) |
    | &nbsp;&nbsp;&nbsp;&nbsp; concertTitle  | String | 콘서트 명                           |
    

***********************************

# 4. 잔액
## 4-1. 잔액 조회
### REQUEST
  - #### Method
  	| Method | URL               |
	|--------|-------------------|
    | GET    | /balance/{userId} |
	
    - #### Header
    | Parameter    | Description                                     |
    |--------------|-------------------------------------------------|
    | Content-Type | <span style="color:red">application/json</span> |
    
    - #### Path Parameter
    | Name   | Type | Description | Required |
    |--------|------|-------------|----------|
    | userId | Int  | 유저 ID       | 필수       |

### RESPONSE
  - | Name                 | Type   | Description |
	|----------------------|--------|-------------|
    | code                 | Int    | 응답 코드       |
    | status               | String | 응답 상태       |
    | message              | String | 응답 메시지      |
    | data                 | Object | 응답 데이터      |
    | &nbsp;&nbsp; id      | Int    | 유저 ID       |
    | &nbsp;&nbsp; name    | String | 유저 명        |
    | &nbsp;&nbsp; transaction | Int    | 잔액          |
    
	<br>

## 4-2. 잔액 충전
### REQUEST
  - #### Method
  	| Method | URL               |
	|--------|-------------------|
    | PATCH  | /transaction/{userId} |
	
  - #### Header
	| Parameter    | Description                                     |
	|--------------|-------------------------------------------------|
	| Content-Type | <span style="color:red">application/json</span> |
    
  - #### Path Parameter
	| Name   | Type | Description | Required |
	|--------|------|-------------|----------|
	| userId | Int  | 유저 ID       | 필수       |
    
  - #### Body
	| Name    | Type | Description | Required |
	|---------|------|-------------|----------|
	| transaction | Int  | 충전금액        | 필수       |

### RESPONSE
  - | Name                 | Type   | Description |
	|----------------------|--------|-------------|
    | code                 | Int    | 응답 코드       |
    | status               | String | 응답 상태       |
    | message              | String | 응답 메시지      |
    | data                 | Object | 응답 데이터      |
    | &nbsp;&nbsp; id      | Int    | 유저 ID       |
    | &nbsp;&nbsp; name    | String | 유저 명        |
    | &nbsp;&nbsp; transaction | Int    | 잔액          |
