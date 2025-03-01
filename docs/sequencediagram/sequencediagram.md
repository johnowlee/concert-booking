# 시퀀스 다이어그램
## 대기열
- ### 대기열 토큰 조회
  ![queue_search_sequence_diagram.png](queue%2Fqueue_search_sequence_diagram.png)
- ### 대기열 토큰 생성
  ![queue_create_sequence_diagram.png](queue%2Fqueue_create_sequence_diagram.png))

---

## 콘서트
- ### 콘서트(옵션) 조회
  ![concert_search_sequence_diagram.png](concert%2Fconcert_search_sequence_diagram.png)

---
## 예약
- ### 유효성 검증
  - #### 미등록 토큰
    ![validation_unregistered_token_sequence_diagram.png](booking%2Fvalidation_unregistered_token_sequence_diagram.png)
  - #### 대기 토큰
    ![validation_waiting_queue_sequence_diagram.png](booking%2Fvalidation_waiting_queue_sequence_diagram.png)
  - #### 잘못된 요청
    ![validation_badrequest_sequence_diagram.png](booking%2Fvalidation_badrequest_sequence_diagram.png)
- ### 예약
  ![booking_sequence_diagram.png](booking%2Fbooking_sequence_diagram.png)
- ### 예약 결제
  ![payment_sequence_diagram.png](booking%2Fpayment_sequence_diagram.png)

---
## 잔액
- ### 잔액 조회
  ![balance_search_sequence_diagram.png](transaction%2Fbalance_search_sequence_diagram.png)
- ### 잔액 충전
  ![balance_charge_sequence_diagram.png](transaction%2Fbalance_charge_sequence_diagram.png)