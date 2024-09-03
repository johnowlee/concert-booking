
# 콘서트 예약 서비스
- 대기열을 구현한 콘서트 예매 API 서비스
- 콘서트, 콘서트 옵션, 콘서트 좌석 조회
- 토큰을 발급 요청 후 동시접속자 수에 따라 활성 또는 대기 토큰 생성
- 콘서트 예약 및 결제는 활성 토큰으로만 가능
- 동시 접속자가 많아 발급 받은 토큰이 대기 상태일 경우 순번대로 대기 후 진행

***

## 아키텍처
![architecture.png](docs%2Farchitecture.png)
- Clean Architecture와 Layered Architecture의 장점을 살린 아키텍처
- Presentation Layer에서는 도메인이 제공하는 컴포넌트를 의존
- Business Layer에서 Repository를 고수준으로 추상화
- 도메인과 Persistence Layer는 Repository를 의존
- 도메인을 보호하면서 DIP와 OCP을 만족
***

## ERD

![erd.png](docs%2Ferd.png)
***

## 시퀀스 다이어그램 
🔗 [시퀀스 다이어그램](docs%2Fsequencediagram%2Fsequencediagram.md)
***

## API 명세
🔗 [API 명세](docs%2Fapi-spec.md)
***


## 주요 기능 및 처리 방식
🔗 [대기열 구현](docs%2Fqueue%2Fqueue-policy.md)

🔗 [동시성 처리](https://velog.io/@wonholee/%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%B2%98%EB%A6%AC-%EB%AC%B8%EC%A0%9C)

🔗 [인덱스 적용](https://velog.io/@wonholee/DB-%EC%BD%98%EC%84%9C%ED%8A%B8-%EC%98%88%EC%95%BD-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%9D%B8%EB%8D%B1%EC%8A%A4-%EB%8F%84%EC%9E%85%EA%B8%B0)

🔗 [트랜잭션 핸들링](https://velog.io/@wonholee/%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EA%B3%A0%EC%B0%B0)
