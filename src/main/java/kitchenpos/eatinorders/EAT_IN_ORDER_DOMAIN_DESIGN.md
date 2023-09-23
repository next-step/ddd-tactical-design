### Eat-In Order Context (매장 주문 컨텍스트)

## 용어사전

### OrderTable (매장테이블)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 매장테이블 | Order Table | 매장 주문을 한 고객이 앉을 수 있는 테이블을 의미. 매장 주문이 매장의 어느 주문 테이블에서 이뤄진 것인지 알기위한 요소 |
| 이름 | Order Table Name | 매장 테이블의 이름. 매장 테이블을 익숙한 언어로 구분할 수 있게해주는 요소. |
| 손님 수 | Number Of Guests | 매장 테이블에 앉은 손님 수. |
| 사용 여부 | Occupied | 매장 테이블의 사용 여부. 참이면 사용 중, 거짓이면 미사용 중 을 의미 |
| 사용 | Sit | 매장 테이블 사용 상태 |
| 미사용 | Clear | 매장 테이블 미사용 상태 |

### Eat-In Order (매장 주문)
주문 상태 흐름 : 승인 대기 중 → 승인 → 서빙 완료 → 주문 완료

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 매장 주문 | Eat-In Order | 매장을 이용하는 주문 |
| 주문 품목 | Eat-In Order Line Item | 주문안에 포함된 품목 |
| 주문된 메뉴 | OrderedMenu | 주문한 시점의 메뉴 정보를 의미. 메뉴 가격의 변경에 매장 주문이 영향받지 않기위해 당시의 정보를 저장한다. |
| 주문 일시 | Order DateTime | 주문이 요청 시간 |
| 주문 품목 가격 | Price | 하나의 주문 품목 가격. 주문 품목 금액을 결정짓는 중요한 요소. 연관된 Menu의 Total Amount 와 같다. |
| 주문 품목 개수 | Quantity | 하나의 주문 품목이 몇 개 주문했는지를 의미. 주문 품목 금액을 결정짓는 중요한 요소 |
| 주문 품목 금액 | Amount | 하나의 주문 품목 금액. `Price`에 `Quantity`를 곱한 결과이다.(`Price` x `Quantity`). 주문의 총 금액을 결정짓는 중요한 요소 |
| 총 금액 | Total Amount | 주문의 최종 금액. 주문 내 모든 주문 품목의 Amount 를 더한 결과이다. |
| 매장 주문 상태 | Order Status | 매장 주문의 현재 상태를 의미. 4가지 상태를 가진다(Waiting, Accepted, Served, Completed) |
| 승인 대기 중 | Waiting | 주문 승인 대기 중인 상태 |
| 승인 | Accepted | 주문 승인 상태 |
| 서빙 완료 | Served | 조리가 완료되어 손님에게 서빙이 완료된 상태 |
| 주문 완료 | Completed | 주문 완료 상태 |

---

## 모델링

### OrderTable (매장 테이블)
#### 속성
- Order Table Name은 필수값이다.
- Occupied를 가진다.
- Number Of Guests를 가진다.
- Number Of Guests는 0명 이상이어야 한다.

#### 행위
- OrderTable을 등록할 수 있다.
- Sit 할 수 있다.
- Clear 될 수 있다.
  - 식사를 완료했다면 Clear할 수있다.
- Number Of Guests를 변경할 수 있다.
  - Clear 된 OrderTable은 Number Of Guests를 변경할 수 없다.

### Eat-In Order (매장 주문)
#### 속성
- OrderStatus를 가진다.
- Eat-In Order는 Eat-In Order Line Item을 1개 이상 가진다.
- OrderDateTime을 가진다.
- OrderTableId는 필수다.
- Eat-In Order는 Waiting, Accepted, Served, Completed 4개의 OrderStatus를 가진다.

#### 행위
- Eat-In Order의 OrderStatus는 Waiting → Accepted → Served → Completed 순서를 가진다.
  - 명시된 순서를 지키지 않으면 Order Status는 변경할 수 없다.
- Eat-In Order가 새로 생성되면 Order Status는 Waiting이다.
  - Eat-In Order Line Item은 주문할 당시의 Menu Price를 가진다.
    - Eat-In Order등록 후에 Menu의 Price가 변경되더라도 Eat-In Order의 Price는 변경이 없어야한다.
    - Eat-In Order Line Item의 Price 또한 변경되지 않는다. (Eat-In Order Line Item의 Quantity에 변경이 있지 않는 한)
  - Eat-In Order는 Eat-In Order Line Item을 1개 이상 가진다.
  - Eat-In Order는 Hide된 Menu를 주문할 수 없다.
  - Eat-In Order는 빈 테이블에 주문할 수 없다.
- Waiting Order를 Accepted 한다.
  - Waiting인 Eat-In Order만 Accepted할 수 있다.
- Accepted Order를 Served한다.
  - Accepted인 Eat-In Order만 Served할 수 있다.
- Served Order를 Completed한다.
  - Served인 Eat-In Order만 Completed 할 수 있다.
  - 주문이 Completed된 OrderTable을 Clear한다.
