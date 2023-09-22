# 키친포스

## 퀵 스타트

```sh
cd docker
docker compose -p kitchenpos up -d
```

## 요구 사항

### 상품

- 상품을 등록할 수 있다.
- 상품의 가격이 올바르지 않으면 등록할 수 없다.
  - 상품의 가격은 0원 이상이어야 한다.
- 상품의 이름이 올바르지 않으면 등록할 수 없다.
  - 상품의 이름에는 비속어가 포함될 수 없다.
- 상품의 가격을 변경할 수 있다.
- 상품의 가격이 올바르지 않으면 변경할 수 없다.
  - 상품의 가격은 0원 이상이어야 한다.
- 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
- 상품의 목록을 조회할 수 있다.

### 메뉴 그룹

- 메뉴 그룹을 등록할 수 있다.
- 메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.
  - 메뉴 그룹의 이름은 비워 둘 수 없다.
- 메뉴 그룹의 목록을 조회할 수 있다.

### 메뉴

- 1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.
- 상품이 없으면 등록할 수 없다.
- 메뉴에 속한 상품의 수량은 0 이상이어야 한다.
- 메뉴의 가격이 올바르지 않으면 등록할 수 없다.
  - 메뉴의 가격은 0원 이상이어야 한다.
- 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
- 메뉴는 특정 메뉴 그룹에 속해야 한다.
- 메뉴의 이름이 올바르지 않으면 등록할 수 없다.
  - 메뉴의 이름에는 비속어가 포함될 수 없다.
- 메뉴의 가격을 변경할 수 있다.
- 메뉴의 가격이 올바르지 않으면 변경할 수 없다.
  - 메뉴의 가격은 0원 이상이어야 한다.
- 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
- 메뉴를 노출할 수 있다.
- 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.
- 메뉴를 숨길 수 있다.
- 메뉴의 목록을 조회할 수 있다.

### 주문 테이블

- 주문 테이블을 등록할 수 있다.
- 주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.
  - 주문 테이블의 이름은 비워 둘 수 없다.
- 빈 테이블을 해지할 수 있다.
- 빈 테이블로 설정할 수 있다.
- 완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.
- 방문한 손님 수를 변경할 수 있다.
- 방문한 손님 수가 올바르지 않으면 변경할 수 없다.
  - 방문한 손님 수는 0 이상이어야 한다.
- 빈 테이블은 방문한 손님 수를 변경할 수 없다.
- 주문 테이블의 목록을 조회할 수 있다.

### 주문

- 1개 이상의 등록된 메뉴로 배달 주문을 등록할 수 있다.
- 1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.
- 1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.
- 주문 유형이 올바르지 않으면 등록할 수 없다.
- 메뉴가 없으면 등록할 수 없다.
- 매장 주문은 주문 항목의 수량이 0 미만일 수 있다.
- 매장 주문을 제외한 주문의 경우 주문 항목의 수량은 0 이상이어야 한다.
- 배달 주소가 올바르지 않으면 배달 주문을 등록할 수 없다.
  - 배달 주소는 비워 둘 수 없다.
- 빈 테이블에는 매장 주문을 등록할 수 없다.
- 숨겨진 메뉴는 주문할 수 없다.
- 주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.
- 주문을 접수한다.
- 접수 대기 중인 주문만 접수할 수 있다.
- 배달 주문을 접수되면 배달 대행사를 호출한다.
- 주문을 서빙한다.
- 접수된 주문만 서빙할 수 있다.
- 주문을 배달한다.
- 배달 주문만 배달할 수 있다.
- 서빙된 주문만 배달할 수 있다.
- 주문을 배달 완료한다.
- 배달 중인 주문만 배달 완료할 수 있다.
- 주문을 완료한다.
- 배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.
- 포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.
- 주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.
- 완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.
- 주문 목록을 조회할 수 있다.

## 용어 사전

## Product (상품)

### Product (상품)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | Product | 메뉴를 구성하는 최소 단위 |
| 이름 | Name | 상품의 이름 |
| 이름 정책 | Name Policy | 상품의 이름을 만드는 정책. |
| 가격 | Price | 상품의 가격 |
| 비속어 | profanity | 고객에게 불쾌함을 줄 수 있는 언어. 상품의 이름은 비속어를 포함하지 않아야한다. |

## 메뉴

### MenuGroup (메뉴그룹)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 메뉴그룹 | Menu Group | 메뉴를 모아놓은 그룹. ex) 식사류, 면류, 사이드 등 메뉴를 분류할 때 사용 |
| 이름 | Name | 메뉴그룹의 이름 |

### Menu (메뉴)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 메뉴 | Menu | 메뉴 그룹에 속하는 실제 주문 가능 단위. 하나의 메뉴는 하나이상의 상품으로 구성 |
| 가격 | Price | 메뉴의 가격. `메뉴의 가격 <= 총 금액`을 지켜야한다. |
| 메뉴상품 | Menu Product | 메뉴에 포함된 상품. 수량을 가지고 있디. 각 메뉴 상품의 가격은 (상품가격 * 수량) 이다 |
| 금액 | Amount | 메뉴 상품의 가격 * 수량의 결과. |
| 총 금액 | Total Amount | 메뉴가 가진 모든 메뉴 상품의 (가격 * 수량) 총합. |
| 이름 | Name | 메뉴의 이름 |
| 노출 | Display | 메뉴를 고객에게 노출 |
| 비노출 | Hide | 메뉴를 고객에게 비노출. 메뉴의 가격이 메뉴 상품들의 총 금액 높은경우 비노출된 상태가 될 수 있다. |
| 비속어 | profanity | 고객에게 불쾌함을 줄 수 있는 언어. 메뉴의 이름은 비속어를 포함하지 않아야한다. |

## Delivery Order (배달 주문)

### Delivery Order (배달 주문)
주문 상태 흐름 : 승인 대기 중 → 승인 → 조리 완료 → 배달 중 → 배달 완료 → 주문 완료

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 주문 상태 | Order Status | 주문의 상태 |
| 주문 품목 | Order Line Item | 주문안에 포함된 품목 |
| 주문 일시 | Order DateTime | 주문이 요청 시간 |
| 주문 품목 가격 | Price | 하나의 주문 품목 가격. 주문 품목 금액을 결정짓는 중요한 요소. 연관된 Menu의 Total Amount 와 같다. |
| 주문 품목 개수 | Quantity | 하나의 주문 품목이 몇 개 주문했는지를 의미. 주문 품목 금액을 결정짓는 중요한 요소 |
| 주문 품목 금액 | Amount | 하나의 주문 품목 금액. `Price`에 `Quantity`를 곱한 결과이다.(`Price` x `Quantity`). 주문의 총 금액을 결정짓는 중요한 요소 |
| 총 금액 | Total Amount | 주문의 최종 금액. 주문 내 모든 주문 품목의 Amount 를 더한 결과이다. |
| 배달 주문 | Delivery Order | 손님이 요청한 배달 주문. 메뉴 정보와 배달 정보를 가지고있다. |
| 배달 주소 | Delivery Address | 배달 요청 주소지 |
| 배달 기사 | Kitchen Rider | 배달하는 기사 |
| 승인 대기 중 | Waiting | 주문 승인 대기 중인 상태 |
| 승인 | Accepted | 주문 승인 상태 |
| 조리 완료 | Served | 조리가 완료되어 배달 기사가 가져갈 수 있는 상태 |
| 배달 중 | Delivering | 배달 기사가 주문 품목을 가져가서 배달 중인 상태 |
| 배달 완료 | Delivered | 고객에게 전달되어 배달 완료된 상태 |
| 주문 완료 | Completed | 주문 완료 상태 |

## Eat-In Order (매장 주문)

### OrderTable (매장테이블)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 매장테이블 | OrderTable | 매장 주문을 한 고객이 앉을 수 있는 테이블을 의미. 매장 주문이 매장의 어느 주문 테이블에서 이뤄진 것인지 알기위한 요소 |
| 이름 | Name | 매장 테이블의 이름. 매장 테이블을 익숙한 언어로 구분할 수 있게해주는 요소. |
| 손님 수 | Number Of Guests | 매장 테이블에 앉은 손님 수. |
| 사용 여부 | Occupied | 매장 테이블의 사용 여부. 참이면 사용 중, 거짓이면 미사용 중 을 의미 |
| 사용 | Sit | 매장 테이블 사용 상태 |
| 미사용 | Clear | 매장 테이블 미사용 상태 |

### Eat-In Order (매장 주문)
주문 상태 흐름 : 승인 대기 중 → 승인 → 서빙 완료 → 주문 완료

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 주문 상태 | Order Status | 주문의 상태 |
| 주문 품목 | Order Line Item | 주문안에 포함된 품목 |
| 주문 일시 | Order DateTime | 주문이 요청 시간 |
| 주문 품목 가격 | Price | 하나의 주문 품목 가격. 주문 품목 금액을 결정짓는 중요한 요소. 연관된 Menu의 Total Amount 와 같다. |
| 주문 품목 개수 | Quantity | 하나의 주문 품목이 몇 개 주문했는지를 의미. 주문 품목 금액을 결정짓는 중요한 요소 |
| 주문 품목 금액 | Amount | 하나의 주문 품목 금액. `Price`에 `Quantity`를 곱한 결과이다.(`Price` x `Quantity`). 주문의 총 금액을 결정짓는 중요한 요소 |
| 총 금액 | Total Amount | 주문의 최종 금액. 주문 내 모든 주문 품목의 Amount 를 더한 결과이다. |
| 매장 주문 | Eat-In Order | 매장을 이용하는 주문 |
| 승인 대기 중 | Waiting | 주문 승인 대기 중인 상태 |
| 승인 | Accepted | 주문 승인 상태 |
| 서빙 완료 | Served | 조리가 완료되어 손님에게 서빙이 완료된 상태 |
| 주문 완료 | Completed | 주문 완료 상태 |

## Take-Out Order (포장 주문)

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

### Profanity (비속어)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 비속어 검사 | Profanity Check | 욕설등의 부적절한 언어가 포함되어있는지 purgomalum 외부 서비스를 통해 contains profanity 검사 |

---

## 모델링

## Product (상품)

### Product (상품)

#### 속성
- Name은 필수값이다.
- Price는 0원 이상이어야 한다.

#### 행위
- Product를 등록할 수 있다.
  - Name은 Profanity Check한다.
- Price는 변경할 수 있다.
  - Price가 변경될 때 해당 Product가 포함된 Menu의 Price가 Menu에 속한 Product Price의 총합보다 비싸진 경우 Menu를 Hide한다.
    - 요약. (Product가 속한 메뉴의 현재 가격 > 해당 Menu에 속한 Product Price의 총합)인 경우 Menu를 Hide 한다.

## Menu (메뉴)

### MenuGroup (메뉴 그룹)
#### 속성
- Name은 필수값이다.

#### 행위
- MenuGroup을 등록할 수 있다.

### Menu (메뉴)
#### 속성
- Name은 필수값이다.
- Price는 0원 이상이어야 한다.
  - Menu의 가격은 Menu에 속한 상품 금액의 합보다 작거나 같아야 한다.
- Menu Group을 필수로 가진다.
- Menu Product를 필수로 1개이상 가진다.
- Menu Product의 Price의 총합보다 클 수 없다.
- Menu의 Displayed는 변경 가능하다.

#### 행위
- Menu를 등록할 수 있다.
  - Name은 Profanity Check한다.
- Price는 변경할 수 있다.
- Menu를 Display한다.
  - Menu의 Price가 Menu에 속한 Menu Product 금액의 합보다 비싼 경우 Menu를 Display할 수 없다.
- Menu를 Hide한다.
  - Menu의 Price가 Menu에 속한 Product Price의 합보다 높을 경우 Menu를 Hide한다.

## Eat-In Order (매장 주문)

### OrderTable (매장 테이블)
#### 속성
- Name은 필수값이다.
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
- Eat-In Order는 Order Line Item 1개 이상 가진다.
- OrderDateTime을 가진다.
- OrderLineItem 1개 이상 가진다.
- OrderTableId는 필수다.
- Eat-In Order는 Waiting, Accepted, Served, Completed 4개의 OrderStatus를 가진다.

#### 행위
- Eat-In Order의 OrderStatus는 Waiting → Accepted → Served → Completed 순서를 가진다.
  - 명시된 순서를 지키지 않으면 Order Status는 변경할 수 없다.
- Eat-In Order가 들어오면 Order Status는 Waiting이다.
  - Eat-In Order는 Hide된 Menu를 주문할 수 없다.
  - Eat-In Order는 빈 테이블에 주문할 수 없다.
- Waiting Order를 Accepted 한다.
- Accepted Order를 Served한다.
- Served Order를 Completed한다.
  - 주문이 Completed된 OrderTable을 Clear한다.
- OrderLineItem은 주문할 당시의 메뉴 가격을 가진다.
  - 주문 후에 Menu의 가격이 변경되더라도 주문의 가격은 변경이 없어야한다.

## Delivery Order (배달주문)

### Delivery Order (배달주문)
#### 속성
- OrderStatus를 가진다.
- DeliveryOrder Order Line Item 1개 이상 가진다.
- OrderDateTime을 가진다.
- 주문은 Hide된 Menu를 주문할 수 없다.
- Order Line Item 1개 이상 가진다.
- Delivery Address는 필수값이다.
- Take-Out Order는 Waiting, Accepted, Served, Delivering, Delivered, Completed 6개의 OrderStatus를 가진다.

#### 행위
- Delivery Order의 OrderStatus는 Waiting → Accepted → Served → Delivering → Delivered → Completed 순서를 가진다.
  - 명시된 순서를 지키지 않으면 Order Status는 변경할 수 없다.
- DeliveryOrder가 들어오면 Order Status는 Waiting이다.
- Waiting Order를 Accepted 한다.
  - Kitchen Rider에게 Delivery Address, Price를 전달한다.
- Accepted Order를 Served한다.
- Served Order를 Kitchen Rider가 Delivering한다.
- Delivering Order를 손님이 받으면 Delivered한다.
- Delivered Order를 Completed한다.

## Take-Out Order (포장주문)

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
