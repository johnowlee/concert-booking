쿼리에 인덱스를 적용해 개선된 부분을 확인하고 싶어 요구사항을 하나 추가했다.

요구사항은 유저들의 포인트 충전/사용 히스토리를 관리하는 테이블에서 사용자별로 특정기간 동안 충전/사용액을 조회하는 기능이다. 인덱스 성능 차이를 유의미하게 확인하기 위해 약 60만건의 데이터를 테이블에 INSERT하여 테스트환경을 구성하였다. 쿼리는 다음과 같이 수행했다.
>select sum(amount) from balance_history
where user_id = 1
and date(transaction_date_time) between date_add(now(), interval -100 day) and now()
and transaction_type = 'USE';

<br>

### 1. 인덱스 없는 쌩쿼리
- 쿼리 실행계획
> ![](https://velog.velcdn.com/images/wonholee/post/3a9e6730-a922-487b-8608-e0094aa43938/image.png) ![](https://velog.velcdn.com/images/wonholee/post/c1f9755d-2cb1-464b-b9cf-0202594d9c7f/image.png)
- 'type'은 'ALL'로 테이블을 Full Scan 했다.
- 'rows'는 560,179건을 처리했다.
- 'Extra'는 'Using Where'로 where절로 데이터를 추출했다.
- Query cost는 약 56,555 이다.

- 쿼리 속도
    - 10회 쿼리 평균 0.2875 sec

<br>


### 2. 인덱스 적용 (user_id, transaction_date_time, transaction_type)
- 쿼리 실행계획
>![](https://velog.velcdn.com/images/wonholee/post/409bf491-c09f-4492-992a-b8352f455899/image.png)![](https://velog.velcdn.com/images/wonholee/post/dcc0da08-bbd9-4cc4-896b-20002efb0696/image.png)
- 'type'은 'ref'로 인덱스를 사용했다.
- 'rows'는 202,224건을 처리했다.
- 'Extra'는 'Using index condition'으로  인덱스를 사용하여 쿼리의 조건을 필터링했다.
- Query cost는 30979 이다.

- 쿼리 속도
    - 10회 쿼리 평균 0.0421 sec

<br>
<br>


<center><b><h3>인덱스 적용결과</h3></b></center>
<center><b><h4>쿼리 성능이 약 6.8배 증가!</h4></b></center>

<br>
쿼리가 특정기간 내에 충전/사용 총액을 조회하는 것이면 SUM을 하는'amount' 컬럼을 인덱스에 같이 설정하면 <b>커버링 인덱스</b>로 테이블 조회없이 쿼리성능을 더 개선할 수 있지 않을까?라는 기대로 'amount' 컬럼도 인덱스에 추가해서 테스트 해보았다.

<br>
<br>


### 3. 커버링 인덱스 적용 (user_id, transaction_date_time, transaction_type, amount)
- 쿼리 실행계획
>![](https://velog.velcdn.com/images/wonholee/post/6cce08e9-d14d-4f75-a7b2-ddffb43ad302/image.png)![](https://velog.velcdn.com/images/wonholee/post/884bdcc0-1885-42f1-875c-feba36a01a06/image.png)
- 'type'은 'ref'로 인덱스를 사용했다.
- 'rows'는 205,022를 처리했다.
- 'Extra'는 'Using Where; Using index'로 커버링 인덱스를 사용하였고 where절로 데이터를 추출했다.
- Query cost는 약 21,402 이다.

- 쿼리 속도
    - 10회 쿼리 평균 0.0641 sec

<br>
<br>

<center> <h3> 커버링 인덱스 적용결과 </h3> </center>

<center>일반 인덱스 쿼리 평균 0.0421 초!</center>
<center>커버링 인덱스 쿼리 평균 0.0641 초... ?</center>
<br>

커버링 인덱스 성능이 더 높을 것으로 기대했는데 결과는 달랐다. 이건 예상치 못한 결과인데, 이해가 잘 되지 않았다. 원인이 될만한 부분을 몇가지 분석해보았다.
1. 커버링 인덱스는 'Using Where'가 의심된다.
- 일반 인덱스를 적용했을때에는 쿼리 실행결과에 'Extra'에서 'Using Where'은 없었다.
- 커버링 인덱스에서 'Using index'로 커버링 인덱스가 먹힌 듯 하나 'Using Where'이 추가 되었다.
- 결과적으로 인덱스 자체적으로 필터링하지 못하고 SQL 엔진에서 추가적으로 Where절에 대한 부분을 검증하는 것 같다.
  <br>

2. 인덱스가 조건들을 자체적으로 필터링 하지 못하는 이유
1) 조건의 복잡성
   - 범위 검색이나 패턴 매칭, 특정 함수의 결과등은 인덱스의 도움을 받지만, 추가 검증이 필요할 수도 있다.

2) 데이터 타입 및 연산
   - 쿼리에서 사용하는 데이터 타입과 연산이 인덱스 최적화와 완벽하게 일치하지 않을 때 SQL 엔진이 인덱스에 데이터를 가져온 후 최종적으로 추가 검증을 할 수도 있다.
