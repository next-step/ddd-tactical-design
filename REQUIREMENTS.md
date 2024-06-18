## 과제 요구사항

1. 상품 context 리팩터링
- 도메인 계층 구현하기
- 도메인 서비스 
- 간접 참조로 aggregate 구현하기
- REPOSITORY 구현
- Validator 구현
- 도메인 부분에서 VO 랑 AGGGREGATE 용
  - 식별을 위한 ID, 추가
  - VALIDATION을 위한 값들 추가
  - 테스트 작성

  

3. EAT_IN ORDER 리팩터링 구현사항
A. 연관관계 설정 및 VO 및 도메인 로직 구현
- ORDER (바운디드 컨텍스트는 넓게 잡기가 선호되서 하나의 패키지로 구현하였습니다.)
  - EAT_IN
  - DELIVERY
    - DELIVERY 주문의 외부 연동 도메인 로직을 DOMAIN SERVICE로 주입
  - TAKEOUT
  - 생성 도메인 로직
  - 상태 변환 도메인 로직
  - FACTORY로 각 ORDER에 대한 생성로직 따로 클래스로 생성

- ORDER ITEMS
  - 생성 도메인로직
    - 메뉴에 대한 의존성을 MenuClient로 가져오는 객체는 OrderMenu로 명시적 표기
    - N+1 문제를 쿼리 한번으로 변경

- ORDERTABLE
  - 생성 도메인 로직
  - 치우기 도메인 로직
    - OrderTable에서 Order에 대해 접근할때, domain service로 주입
- ORDER SERVICE
  - 서비스에 있는 도메인 로직 도메인 객체안으로 넣기

B. @DOMAIN SERVICE 와 @DOMAIN FACTORY에 대한 에노테이션 생성으로 명시적 표기


1. 책에 따라 DOMAIN SERVICE를 METHOD 호출시 전달하게끔 변경하였습니다. '외부 시스템 연동이 필요한 도메인 로직일때 혹은 계산로직일때'


2. 1-N(주문, 주문항목들) 의 경우 N이 많아질 때가 있는데 해당부분은 어떻게 구현하실지 궁금합니다.


3. 주문 타입의 경우가 많아지면, 어떤 타입 -> 팩토리에 대한 맵핑을 지원하는 디자인 패턴을 사용하시나요?


4. 지금 상태에서 실무에 돌입하게 되면 ORDER 와 EATIN ORDER의 관계에서 상속이 객체지향적이지 않아 보여요! (EJ 합성 > 상속) 
   - 웅래님은 만약 실무였다면, 어떻게 구현하셨을지 궁금합니다.
   - 저라면, 공유커널(COMMON)에 빈 껍데기 ORDER, ORDERITEMS를 두기
     - 공유커널을 상속받아서 DOMAIN ENTITY들을 각각 EATIN_ORDER, DELIVERY_ORDER, TAKEOUT_ORDER 패키지에서 각각 상속받아서 생성했을것같아요

5. 마지막으로, 공유커널(COMMON)에 있는 이벤트들은 내부 이벤트(내부 서비스) VS 외부 이벤트(외부 서비스) 들이 있을것 같아요
   - 보통은 어떻게 나누시나요? (인터페이스?, 마커 인터페이스?)

6. 이건 사적인 질문입니다!(답변안해주셔도 되요!) 실무에서 ORDER 같은 객체는 요구사항에 의해 자주 변동이 되는데 BUILDER 패턴에 

