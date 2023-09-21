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

### 상품
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | Product | 메뉴에 등록될 음식을 말한다 | 
| 상품명 | Displayed Name | 상품명을 말한다 | 
| 비속어 | Slang | 비속어 정책에 정의된 사이트에서 필터링 된 단어 |
| 가격 | Price | 상품 가격을 말한다 |
| 상품 등록 | Register Product | (새로운)상품을 등록하는 것을 말한다 | 
| 상품 가격 변경 | Change the Price | 상품 가격을 변경하는 것을 말한다 | 
| 상품 목록 조회 | View All Products | 전체 상품을 조회하는 것을 말한다 | 


### 매장 주문
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 테이블 | Table | 매장 주문을 위해 방문한 손님들이 차지해야하는 테이블 |
| 손님 수 | Number of Guests | 테이블을 사용하고 있는 손님 수를 말한다 |
| 테이블 상태 | TableStatus | 테이블 사용유무 |
| 사용중 | In Use | 테이블을 사용하는 상태 |
| 사용안함 | Not In Use | 테이블을 사용하지 않는 상태 |
| 테이블 정리 | Clean Table | 테이블 상태를 사용안함으로 변경하는 것 |
| 테이블 착석 | Seat on Table | 테이블 상태를 사용중으로 변경하는 것 |
| 테이블 목록 조회 | View All Tables| 전체 테이블을 조회하는 것을 말한다 |
| 주문 | Eat In Order | 음식을 매장에서 먹고갈 경우에 하는 주문 |
| 주문 항목 | Order Line Item | 한 번의 주문에 1개 이상의 메뉴로 주문할 수 있는데, 각각의 주문한 메뉴 항목을 말한다 |
| 주문 항목들 | Order Line Items | Order Line item 의 묶음을 말한다 |
| 주문 상태 | OrderStatus | 주문의 진행상태 |
| 대기중 | Waiting | 매장 주문의 처음 상태 |
| 접수됨 | Accepted | 접수 대기 중인 주문을 확인하고 주문을 접수한 상태 |
| 서빙됨 | Served | 접수된 주문을 음식을 준비하여 서빙한 상태 |
| 완료됨 | Completed | 주문의 마지막 상태. 주문이 손님에게 제공 완료되었음을 의미한다 |

### 포장 주문
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 주문 | Takeout Order | 음식을 매장에서 먹지 않고 포장 요구하는 주문 |
| 주문 항목 | Order Line Item | 한 번의 주문에 1개 이상의 메뉴로 주문할 수 있는데, 각각의 주문한 메뉴 항목을 말한다 |
| 주문 항목들 | Order Line Items | Order Line item 의 묶음을 말한다 |
| 주문 상태 | OrderStatus | 주문의 진행상태 |
| 대기중 | Waiting | 주문의 처음 상태 |
| 접수됨 | Accepted | 접수 대기 중인 주문을 확인하고 주문을 접수한 상태 |
| 서빙됨 | Served | 접수된 주문을 음식을 준비하여 서빙한 상태 |
| 완료됨 | Completed | 주문의 마지막 상태. 주문이 손님에게 제공 완료되었음을 의미한다 |

### 배달 주문
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 주문 | Delivery Order | 음식을 배달 주소에 가져다 주는 주문 |
| 배달 주소 | Delivery Address | 배달 주문의 음식 도착지 |
| 배달 대행사 | Kitchen Riders | 음식을 배달 주소에 운반해주는 업체 |
| 주문 항목 | Order Line Item | 한 번의 주문에 1개 이상의 메뉴로 주문할 수 있는데, 각각의 주문한 메뉴 항목을 말한다 |
| 주문 항목들 | Order Line Items | Order Line item 의 묶음을 말한다 |
| 주문 상태 | Order Status | 주문의 진행상태 |
| 대기중 | Waiting | 주문의 처음 상태 |
| 접수됨 | Accepted | 접수 대기 중인 주문을 확인하고 주문을 접수한 상태 |
| 서빙됨 | Served | 접수된 주문을 음식을 준비하여 서빙한 상태 |
| 배달중 | Delivering | 배달 대행사가 음식을 배달 주소에 운반하고 있는 상태 |
| 배달 완료됨 | Delivery Completed | 배달 대행사가 음식을 배달 주소에 운반 완료한 상태 |
| 완료됨 | Completed | 주문의 마지막 상태. 주문이 손님에게 제공 완료되었음을 의미한다 |

## 모델링

### 상품
#### 속성
- `Product`는 식별자, `Displayed Name` 와 `Price`를 갖는다.
- `Displayed Name`은 이름을 갖는다.
  - 이름은 없거나 빈 값일 수 없다.
  - 이름은 `Slang`를 포함할 수 없다.
- `Price`는 가격을 갖는다.
  - 가격은 0원 이상이어야 한다.
#### 기능
- `Register Product`를 할 수 있다.
  - `Register Product` 시, `Displayed Name`과 `Price`는 필수여야 한다.
  - `Register Product` 시, `Price`는 0원 이상이다.
- `Change the price`를 할 수 있다.
  - `Change the price` 시, 변경될 `Price`는 0원 이상이여야 한다.
  - `Change the price` 시, Menu Display Policy를 따른다.
- `View All Products`가 가능하다.

#### 정책
- 메뉴노출정책(Menu Display Policy)
  - `Product`의 `Change the Price`가 일어났을 때, 메뉴가격정책을 만족하지 못한 메뉴는 숨겨진다
- 메뉴가격정책(Menu Price Policy)
  - `Menu`의 `Price`는 `Menu Products`의 `Price` 총합보다 작거나 같아야 한다
- 비속어정책(Slang Policy)
  - `https://www.purgomalum.com/service/containsprofanity`에서 단어를 필터링 한다


### 매장 주문
#### 속성
- `Table`은 식별자, 이름, `손님 수`를 갖는다.
#### 기능
- `Table`을 등록할 수 있다.
  - `Table` 등록 시, 테이블 상태는 `Not In Use`이다.
- `Table`의 `손님 수`를 변경 할 수 있다.
  - `Table`의 `손님 수` 변경 시, `손님 수`는 0명 이상이여야 한다.
- `Table`은 `Clean Table` 할 수 있다.
  - 테이블 상태를 `Not In Use`로 변경과 `손님 수`를 0으로 만든다.
- `Table`은 `Seat on Table` 할 수 있다.
  - 테이블 상태를 `In Use`로 변경한다.


#### 속성
- `EatInOrder`는 `Table`의 식별자, `OrderStatus`, 주문시각, `OrderLineItems`를 갖는다.
- `OrderStatus`는 `Waiting`, `Accepted`, `Served`, `Completed` 을 갖는다.
  - `OrderStatus`는 순서를 갖는데, (`Waiting` > `Accepted` > `Served` > `Completed`)와 같이 진행된다.
- Quantity는 수량을 갖는다.
  - 수량은 0개 이상이어야 한다.
- `OrderLineItems`는 `OrderLineItem`을 갖는다.
- `OrderLineItem`은 `Menu`의 식별자, `Price`, Quantity를 갖는다.
  - `OrderLineItem`의 `Price`와 `Menu`의 `Price`는 같아야한다.
#### 기능
- `EatInOrder`는 생성할 수 있다.
  - `EatInOrder` 생성 시, `Table`은  `In Use` 여야 한다.
  - `Menu`는 Displayed Menu 여야 한다.
- `EatInOrder`는 접수할 수 있다.
- `EatInOrder`는 서빙할 수 있다.
- `EatInOrder`는 완료할 수 있다.
  - `EatInOrder`가 완료될 때, EatInOrder Complete Policy를 따른다.

#### 정책
- 매장주문완료정책(EatInOrderCompletePolicy)
  - `EatInOrder`가 Complete 되었을때, 해당 `EatInOrder`와 관련된 `Table`의 모든 `EatInOrder`가 `Completed`면 `Clean Table`한다


### 포장 주문
#### 속성
- `TakeOutOrder`는 `OrderStatus`, 주문시각, `OrderLineItems`를 갖는다.
- `OrderStatus`는 `Waiting`, `Accepted`, `Served`, `Completed` 을 갖는다.
  - `OrderStatus`는 순서를 갖는데, (`Waiting` > `Accepted` > `Served` > `Completed`)와 같이 진행된다.
- Quantity는 수량을 갖는다.
  - 수량은 0개 이상이어야 한다.
- `OrderLineItems`는 `OrderLineItem`을 갖는다.
- `OrderLineItem`은 `Menu`의 id, `Price`, Quantity를 갖는다.
- `OrderLineItem`의 `Price`와 `Menu`의 `Price`는 같아야한다.
#### 기능
- `TakeOutOrder`는 생성할 수 있다.
  - `Menu`는 Displayed Menu 여야 한다.
- `TakeOutOrder`는 접수할 수 있다.
- `TakeOutOrder`는 서빙할 수 있다.
- `TakeOutOrder`는 완료할 수 있다.


### 배달 주문
#### 속성
- `DeliveryOrder`는 `DeliveryAddress`, `OrderStatus`, 주문시각, `OrderLineItems`를 갖는다.
- `DeliveryAddress`는 주소를 갖는다.
  - 주소는 존재해야한다.
- `OrderStatus`는 `Waiting`, `Accepted`, `Served`, `Delivering`, `Delivery Completed`, `Completed` 을 갖는다.
  - `OrderStatus`는 순서를 갖는데, (`Waiting` > `Accepted` > `Served` > `Delivering` > `Delivery Completed` > `Completed`)와 같이 진행된다.
- Quantity는 수량을 갖는다.
  - 수량은 0개 이상이어야 한다.
- `OrderLineItems`는 `OrderLineItem`을 갖는다.
- `OrderLineItem`은 `Menu`의 id, `Price`, Quantity를 갖는다.
- `OrderLineItem`의 `Price`와 `Menu`의 `Price`는 같아야한다.
#### 기능
- `DeliveryOrder`는 생성할 수 있다.
  - `Menu`는 Displayed Menu 여야 한다.
  - `DeliveryOrder` 생성 시, `DeliveryAddress` 존재해야한다.
- `DeliveryOrder`는 접수할 수 있다.
  - `DeliveryOrder`가 접수되었을 때, DeliveryOrder Accept Policy를 따른다.
- `DeliveryOrder`는 서빙할 수 있다.
- `DeliveryOrder`는 배달시작할 수 있다.
- `DeliveryOrder`는 배달완료할 수 있다.
- `DeliveryOrder`는 완료할 수 있다.

#### 정책
- 배달주문접수정책(Delivery Order Accept Policy)
  - `DeliveryOrder`가 접수 되었을 때, `Kitchen Riders`에게 배달 요청을 한다.
