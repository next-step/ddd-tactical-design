# Take-Out Order Context (포장 주문 컨텍스트)

## 용어사전

### Take-Out Order (포장 주문)

주문 상태 흐름 : 승인 대기중 → 승인 → 포장 완료 → 주문 완료

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 주문 상태 | Order Status | 주문의 상태 |
| 주문 품목 | Order Line Item | 주문안에 포함된 품목 |
| 주문 일시 | Order DateTime | 주문이 요청 시간 |
| 주문 품목 가격 | Price | 하나의 주문 품목 가격. 주문 품목 금액을 결정짓는 중요한 요소. 연관된 Menu의 Total Amount 와 같다. |
| 주문 품목 개수 | Quantity | 하나의 주문 품목이 몇 개 주문했는지를 의미. 주문 품목 금액을 결정짓는 중요한 요소 |
| 주문 품목 금액 | Amount | 하나의 주문 품목 금액. `Price`에 `Quantity`를 곱한 결과이다.(`Price` x `Quantity`). 주문의 총 금액을 결정짓는 중요한 요소 |
| 총 금액 | Total Amount | 주문의 최종 금액. 주문 내 모든 주문 품목의 Amount 를 더한 결과이다. |
| 포장 주문 | Take-Out Order | 포장을 이용하는 주문 |
| 승인 대기 중 | Waiting | 주문 승인 대기 중인 상태 |
| 승인 | Accepted | 주문 승인 상태 |
| 포장 완료 | Served | 조리가 완료되어 고객이 주문한 메뉴를 가져갈 수 있는 상태 |
| 주문 완료 | Completed | 주문 완료 상태 |

---

## 모델링

### Take-Out Order (포장주문)

#### 속성
- OrderStatus를 가진다.
- DeliveryOrder Order Line Item 1개 이상 가진다.
- OrderDateTime을 가진다.
- 주문은 Hide된 Menu를 주문할 수 없다.
- Order Line Item 1개 이상 가진다.
- Take-Out Order는 Waiting, Accepted, Served, Completed 4개의 OrderStatus를 가진다

#### 행위
- Take-Out Order의 OrderStatus는 Waiting → Accepted → Served → Completed 순서를 가진다.
  - 명시된 순서를 지키지 않으면 Order Status는 변경할 수 없다.
- Take-Out Order가 들어오면 Order Status는 Waiting이다.
- Waiting Order를 Accepted 한다.
- Accepted Order를 Served한다.
- Served Order를 Completed한다.
- Order Line Item 의 가격은 실제 Menu의 가격과 일치해야 한다.
