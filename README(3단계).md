## 요구 사항
- 키친포스의 요구 사항과 용어 사전, 모델링을 기반으로 매장 식사 주문 CONTEXT를 리팩터링한다.
- 매장 식사 주문 CONTEXT의 도메인 계층만 먼저 구현한다.
    - `eatinorders` 패키지 밑에 `tobe.domain` 패키지를 만들고 거기서부터 구현을 시작한다.
- 용어 사전과 모델링이 부자연스럽거나 불완전하거나 잘못된 경우 지속적으로 수정한다.
- 새로운 모델에 맞게끔 클래스, 메서드, 모듈의 이름을 다시 지으면서 코드를 리팩터링한다.
- REPOSITORY 구현 시 자신에게 익숙하고 편한 것을 선택하여 진행한다.
- Spring Data JPA
    - [Getting started with Spring Data JPA](https://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa)
    - [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa)
- Spring JDBC
    - [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access)
- 데이터베이스 스키마 변경 및 마이그레이션이 필요하다면 아래 문서를 적극 활용한다.
    - [DB도 형상관리를 해보자!](https://meetup.toast.com/posts/173)


## [모델링]
### 매장 주문

- `Order(주문)` 는 식별자, `OrderStatus(주문 상턔)`, 주문 일시, 여러 개의 `OrderLineItem(주문 항목)`, `OrderTable(주문 테이블)`을 가진다.
- `Order(주문)` 에서 여러 개의 `OrderLineItem(주문 항목)` 를 생성한다.
- `OrderLineItem` 은 `DisplayedMenu(노출된 메뉴)` , `Quantity(수량)`, 총 `Price(가격)` 을 가진다.
- `Order(주문)` 에서 `OrderStatus(주문 상턔)` 를 변경한다.
- `OrderStatus(주문 상턔)` 는 `Waiting` → `Accepted` → `Served` →  `Completed` 를 가진다.
  - 주문된적 없는 주문인 경우 처리가 불가능하다.
- 주문 등록 정책 : `Menu(메뉴)`가 `DisplayedMenu(노출된 메뉴)`이고. `OrderTable(주문 테이블)`이 있어야 등록이 가능하다.

  ```mermaid
  ---
  title: EatIn OrderStatus
  ---
  flowchart LR
    A[Waiting\n접수] --> D(Accepted\n수락)
    D --> E(Served\n서빙 완료)
    E --> F[Completed\n주문 완료]
  ```

##### 주문 테이블

- `OrderTable(주문 테이블)` 은 식별자, `OrderTableName(주문 테이블 이름)`, `NumberOfGuests(손님 수)`, `Occupied(착석여부)` 를 항상가진다.
- `OrderTable(주문 테이블)` 에서 `Occupied(착석여부)`를 `OccupyingTable(착석 테이블)` 또는 `ClearedTable(빈 테이블)` 로 변경한다.
- `ClearedTable(빈 테이블)` 로 변경할 때 `Order(주문)` 의 상태가 `COMPLETED` 여야 한다.
- `ClearedTable(빈 테이블)` 은 `NumberOfGuests(손님 수)` 가 0이고, `Occupied(착석여부)` 가 아닌 상태이다.
- `OrderTable(주문 테이블)` 에서 `NumberOfGuests(손님 수)` 를 변경한다.
- `NumberOfGuests(손님 수)` 는 0명 이상이다.
- `NumberOfGuests(손님 수)` 는 `OccupyingTable(착석 테이블)` 일 때만 가능하다.
