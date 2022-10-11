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

- [ ] 1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.
- [x] 상품이 없으면 등록할 수 없다.
- [ ] 메뉴에 속한 상품의 수량은 0 이상이어야 한다.
- [ ] 메뉴의 가격이 올바르지 않으면 등록할 수 없다.
  - [ ] 메뉴의 가격은 0원 이상이어야 한다.
- [ ] 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
- [ ] 메뉴는 특정 메뉴 그룹에 속해야 한다.
- [x] 메뉴의 이름이 올바르지 않으면 등록할 수 없다.
  - [x] 메뉴의 이름에는 비속어가 포함될 수 없다.
  - [x] 메뉴의 이름에는 null 이나 공백일 수 없다.
- [ ] 메뉴의 가격을 변경할 수 있다.
- [ ] 메뉴의 가격이 올바르지 않으면 변경할 수 없다.
  - [x] 메뉴의 가격은 0원 이상이어야 한다.
- [ ] 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
- [ ] 메뉴를 노출할 수 있다.
- [ ] 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.
- [ ] 메뉴를 숨길 수 있다.
- [ ] 메뉴의 목록을 조회할 수 있다.

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

### 상품

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 이름 | displayed name | 음식을 상상하게 만드는 중요한 요소 |
| 가격 | price | 상품의 가격 |

### 메뉴

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 금액 | amount | 가격 * 수량 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 그룹 | menu group | 각각의 메뉴를 성격에 따라 분류하여 묶어둔 그룹 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 숨겨진 메뉴 | not displayed menu | 주문할 수 없는 숨겨진 메뉴 |
| 이름 | displayed name | 음식을 상상하게 만드는 중요한 요소 |

### 매장 주문

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 방문한 손님 수 | number of guests | 식기가 필요한 사람 수. 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 서빙 | served | 조리가 완료되어 음식이 나갈 수 있는 단계 |
| 완료 | completed | 고객이 모든 식사를 마치고 결제를 완료한 단계 |
| 접수 | accepted | 주문을 받고 음식을 조리하는 단계 |
| 접수 대기 | waiting | 주문이 생성되어 매장으로 전달된 단계 |
| 주문 | order | 매장에서 식사하는 고객 대상. 손님들이 매장에서 먹을 수 있도록 조리된 음식을 가져다준다. |
| 주문 상태 | order status | 주문이 생성되면 매장에서 주문을 접수하고 고객이 음식을 받기까지의 단계를 표시한다. |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |

### 배달 주문

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 배달 | delivering | 배달원이 매장을 방문하여 배달 음식의 픽업을 완료하고 배달을 시작하는 단계 |
| 배달 대행사 | delivery agency | 준비한 음식을 고객에게 직접 배달하는 서비스 |
| 배달 완료 | delivered | 배달원이 주문한 음식을 고객에게 배달 완료한 단계 |
| 서빙 | served | 조리가 완료되어 음식이 나갈 수 있는 단계 |
| 완료 | completed | 배달 및 결제 완료 단계 |
| 접수 | accepted | 주문을 받고 음식을 조리하는 단계 |
| 접수 대기 | waiting | 주문이 생성되어 매장으로 전달된 단계 |
| 주문 | order | 집이나 직장 등 고객이 선택한 주소로 음식을 배달한다. |
| 주문 상태 | order status | 주문이 생성되면 매장에서 주문을 접수하고 고객이 음식을 받기까지의 단계를 표시한다. |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |

### 포장 주문

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 서빙 | served | 조리가 완료되어 음식이 나갈 수 있는 단계 |
| 완료 | completed | 고객이 음식을 수령하고 결제를 완료한 단계 |
| 접수 | accepted | 주문을 받고 음식을 조리하는 단계 |
| 접수 대기 | waiting | 주문이 생성되어 매장으로 전달된 단계 |
| 주문 | order | 포장하는 고객 대상. 고객이 매장에서 직접 음식을 수령한다. |
| 주문 상태 | order status | 주문이 생성되면 매장에서 주문을 접수하고 고객이 음식을 받기까지의 단계를 표시한다. |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |

## 모델링

### 상품
- `Product`는 식별자와 `DisplayedName`, `Price`를 가진다.
- `DisplayedName`에는 `Profanity`가 포함될 수 없다.
- `Price`는 0원 이상이어야 한다.
- `Price`의 가격을 변경할 수 있다.

### 메뉴

- `MenuGroup`은 식별자와 이름을 가진다.
- `Menu`는 식별자와 `Displayed Name`, 가격, `MenuProducts`를 가진다.
- `Menu`는 특정 `MenuGroup`에 속한다.
- `Menu`의 가격은 `MenuProducts`의 금액의 합보다 적거나 같아야 한다.
- `Menu`의 가격이 `MenuProducts`의 금액의 합보다 크면 `NotDisplayedMenu`가 된다.
- `MenuProduct`는 가격과 수량을 가진다.

### 매장 주문

- `OrderTable`은 식별자와 이름, `NumberOfGuests`를 가진다.
- `OrderTable`의 추가 `Order`는 `OrderTable`에 계속 쌓이며 모든 `Order`가 완료되면 `EmptyTable`이 된다.
- `EmptyTable`인 경우 `NumberOfGuests`는 0이며 변경할 수 없다.
- `Order`는 식별자와 `OrderStatus`, 주문 시간, `OrderLineItems`를 가진다.
- 메뉴가 노출되고 있으며 판매되는 메뉴 가격과 일치하면 `Order`가 생성된다.
- `Order`는 접수 대기 ➜ 접수 ➜ 서빙 ➜ 계산 완료 순서로 진행된다.
- `OrderLineItem`는 가격과 수량을 가진다.
- `OrderLineItem`의 수량은 기존 `Order`를 취소하거나 변경해도 수정되지 않기 때문에 0보다 적을 수 있다.

### 배달 주문

- `Order`는 식별자와 `OrderStatus`, 주문 시간, 배달 주소, `OrderLineItems`를 가진다.
- 메뉴가 노출되고 있으며 판매되는 메뉴 가격과 일치하면 `Order`가 생성된다.
- `Order`는 접수 대기 ➜ 접수 ➜ 서빙 ➜ 배달 ➜ 배달 완료 ➜ 계산 완료 순서로 진행된다.
- `Order`가 접수되면 `DeliveryAgency`가 호출된다.
- `OrderLineItem`는 가격과 수량을 가진다.
- `OrderLineItem`의 수량은 1보다 커야 한다.

### 포장 주문

- `Order`는 식별자와 `OrderStatus`, 주문 시간, `OrderLineItems`를 가진다.
- 메뉴가 노출되고 있으며 판매되는 메뉴 가격과 일치하면 `Order`가 생성된다.
- `Order`는 접수 대기 ➜ 접수 ➜ 서빙 ➜ 계산 완료 순서로 진행된다.
- `OrderLineItem`는 가격과 수량을 가진다.
- `OrderLineItem`의 수량은 1보다 커야 한다.
