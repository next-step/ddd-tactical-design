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
- 빈 테이블을 점유할 수 있다.
- 테이블을 비울 수 있다.
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

| 한글명     | 영문명            | 설명                       |
|---------|------------------|--------------------------|
| 상품      | product          | 키친포스에서 판매하는 음식           |
| 가격(금액)  | price            | 키친포스에서 판매하는 상품의 가격       |
| 비속어 검증기 | Purgomalum   | 상품 이름에 포함된 비속어를 검증하는 시스템 |

### 메뉴

| 한글명        | 영문명          | 설명                       |
|------------|--------------|--------------------------|
| 메뉴 그룹      | menu group   | 관련된 메뉴를 묶은 카테고리          |
| 메뉴         | menu         | 손님이 구매 가능한 상품 묶음         |
| 가격(금액)     | price        | 키친포스에서 판매하는 메뉴의 가격       |
| 비속어 검증기 | Purgomalum   | 상품 이름에 포함된 비속어를 검증하는 시스템 |
| 메뉴 구성 상품   | menu product | 메뉴에 구성되는 여러 상품           |
| 메뉴 노출      | display      | 키친포스에서 노출되는 메뉴           |
| 메뉴 숨김      | hide         | 키친포스에서 숨겨진 메뉴            |

#### 메뉴 구성 상품

| 한글명     | 영문명          | 설명            |
|---------|--------------|---------------|
| 메뉴 구성 상품 | menu product | 메뉴에 구성되는 여러 상품 |
| 가격      | price        | 상품의 가격과 수량의 곱 |
| 수량      | quantity         | 상품의 개수        |

### 주문

| 한글명     | 영문명              | 설명                                         |
|---------|------------------|--------------------------------------------|
| 주문 유형   | order type       | 손님이 주문할 수 있는 방법                    |
| 주문한 메뉴  | order line item  | 주문에 포함된 메뉴                           |
| 주문 상태   | order status     | 손님의 주문 진행 상태                        |

#### 배달 주문

| 한글명    | 영문명              | 설명                           |
|--------|------------------|------------------------------|
| 배달 주문  | delivery         | 정해진 위치로 배달해주는 주문             |
| 배달 주소  | delivery address | 배달 주문의 목적지                   |
| 배달 대행사 | kitchen rider    | 배달 주소로 상품을 배달하는 위탁 업체        |
| 접수 대기 중 | waiting          | 매장에서 주문을 접수하기 전의 상태          |
| 접수     | accepted         | 매장에서 주문을 확인한 상태              |
| 서빙     | served           | 매장에서 조리가 완료되어 배달 대행사에게 전달된 상태 |
| 배달 중    | delivering       | 손님에게 배달이 진행 중인 상태                |
| 배달 완료   | delivered        | 손님에게 상품이 전달된 상태                   |
| 주문 완료  | completed        | 주문의 모든 절차가 끝난 상태             |

#### 포장 주문

| 한글명          | 영문명            | 설명                            |
|----------------|------------------|-------------------------------|
| 포장 주문       | take out         | 손님이 매장에 방문해 상품을 가져가야 하는 주문    |
| 접수 대기 중 | waiting          | 매장에서 주문을 접수하기 전의 상태           |
| 접수     | accepted         | 매장에서 주문을 확인한 상태               |
| 서빙      | served           | 매장에서 조리가 완료되어 방문한 손님에게 전달된 상태 |
| 주문 완료  | completed        | 주문의 모든 절차가 끝난 상태              |

#### 매장 주문

| 한글명          | 영문명            | 설명                             |
|----------------|------------------|--------------------------------|
| 매장 주문       | eat in           | 손님이 주문 테이블에서 식사하는 주문           |
| 접수 대기 중 | waiting          | 매장에서 주문을 접수하기 전의 상태            |
| 접수     | accepted         | 매장에서 주문을 확인한 상태                |
| 서빙      | served           | 매장에서 조리가 완료되어 테이블로 전달된 상태 |
| 주문 완료  | completed        | 주문의 모든 절차가 끝난 상태               |

##### 주문 테이블

| 한글명         | 영문명            | 설명                             |
|-------------|------------------|--------------------------------|
| 주문 테이블(테이블) | order table      | 매장에서 식사가 가능한 테이블               |
| 점유          | occupy           | 주문 테이블을 점유                    |
| 비움          | clear            | 주문 테이블을 비움                     |
| 빈 테이블       | cleared table    | 손님이 사용하지 않는 빈 테이블              |
| 손님 수        | number of guests | 주문 테이블에서 식사하는 손님의 수            |

## 모델링

### 상품 (`Product`)

#### 속성

- `Product` 는 `name`, `price` 를 가진다.
  - `name`
    - `name` 은 반드시 필요하고 비속어를 포함하면 안된다. 
  - `price`
    - `price` 는 0 이상이다.

#### use-case

- `Product` 를 생성할 수 있다.
  - 비속어 검증기로 이름을 확인해야 한다. 
- `Product`의 `price`를 변경할 수 있다.
  - `Product`를 포함하고 있는 `Menu`의 모든 `MenuProduct` 가격의 합이 `price`보다 크면 `Menu`를 비노출한다.

### 메뉴 (`Menu`)

#### 속성

- `Menu` 는 `name`, `price`, `MenuGroup`, `displayed` 그리고 `menuProducts` 를 가진다.
  - `name`
    - `name` 은 반드시 필요하고 비속어를 포함하면 안된다.
  - `price`
    - `price` 는 0 이상이다.
  - `menuProducts`
    - `MenuProduct` 의 리스트로, 1개 이상 있어야 한다.

#### use-case

- 사장님이 `Menu` 를 생성할 수 있다.
  - 비속어 검증기로 이름을 확인해야 한다.
  - `Menu`의 모든 `MenuProduct` 가격의 합이 `price` 이하여야 한다.
- 사장님은 `Menu`의 `price`를 변경할 수 있다.
- 사장님은 `Menu`를 노출할 수 있다.
  - `Menu`의 모든 `MenuProduct` 가격의 합이 `price` 이하여야 한다.
- 사장님은 `Menu`를 비노출할 수 있다.
  - ※ `Menu`에 포함된 `Product` 의 가격이 변할 때, `Product`를 포함하고 있는 `Menu`의 모든 `MenuProduct` 가격의 합이 `price`보다 클 때에도 비노출한다.

### 메뉴 구성 상품 (`MenuProduct`)

#### 속성

- `MenuProduct` 는 `Product` 와 `quantity` 를 가진다.
  - `MenuProduct` 의 수량은 0 이상이어야 한다.
  - `MenuProduct` 의 `Product` 는 생성되어 있어야 한다.
  - `MenuProduct` 의 가격은 `Product` 의 `price` 와 `quantity` 의 곱이다.

### 메뉴 그룹 (`MenuGroup`)

#### 속성

- `MenuGroup` 는 `name` 을 가진다.
  - `name` 은 반드시 필요하다.

#### use-case

- 사장님은 `MenuGroup` 을 생성할 수 있다.

### 주문 테이블 (`OrderTable`)

#### 속성

- `OrderTable` 는 `name`, `numberOfGuests` 그리고 `occupied` 를 가진다.
  - `name` 
    - `name` 은 반드시 필요하다.
  - `numberOfGuests`
    - `numberOfGuests` 는 0 이상이어야 한다.

#### use-case

- 사장님이 `OrderTable` 을 생성할 수 있다.
  - `numberOfGuests` 가 0 이고, `occupied` 가 `false` 이다.
- 테이블을 점유된 상태로 변경할 수 있다.
- 테이블을 비울 수 있다.
  - `numberOfGuests` 가 0 이고, `occupied` 가 `false` 이다.
  - `Order`가 `COMPLETED` 일 때만 비울 수 있다.
- 테이블의 손님 수를 변경할 수 있다.
  - `occupied` 가 `true` 여야 한다.

### 주문 (`Order`)

#### 배달 주문

##### 속성

- `type`, `status`, `orderDateTime`, `orderLineItems`, `deliveryAddress` 를 가진다
  - `type` 은 `DELIVERY` 이다.
  - `status` 는 다음 목록 중 하나이다.
    - `WAITING`
    - `ACCEPTED`
    - `SERVED`
    - `DELIVERING`
    - `DELIVERED`
    - `COMPLETED`
  - `status` 의 상태는 다음 순서로 변경된다.
    - `WAITING` → `ACCEPTED` → `SERVED` → `DELIVERING` → `DELIVERED` → `COMPLETED`
  - `deliveryAddress` 가 반드시 필요하다. 
  - `orderLineItems`
    - `OrderLineItem` 의 리스트로, 1개 이상 있어야 한다.
    - `OrderLineItem` 에 포함된 `Menu`는 미리 생성되어 있어야 한다.
    - 비노출 `Menu`가 포함된 `OrderLineItem`은 없어야한다.

##### use-case

- 배달 주문을 생성할 수 있다.
  - `status`는 `WAITING` 이다.
- 배달 주문을 수락할 수 있다. 
  - `status`는 `WAITING` 이어야 한다.
  - `status`를 `ACCEPTED`로 변경한다.
  - 배달 대행사에 배달 정보를 전달해야 한다. 
- 배달 주문을 서빙할 수 있다.
  - `status`는 `ACCEPTED` 이어야 한다.
  - `status`를 `SERVED`로 변경한다.
- 배달을 시작 할 수 있다.
  - `status`는 `SERVED` 이어야 한다.
  - `status`를 `DELIVERING`로 변경한다.
- 배달을 완료 할 수 있다.
  - `status`는 `DELIVERING` 이어야 한다.
  - `status`를 `DELIVERED`로 변경한다.
- 배달 주문을 완료 할 수 있다.
  - `status`는 `DELIVERED` 이어야 한다.
  - `status`를 `COMPLETED`로 변경한다.

#### 포장 주문

##### 속성

- `type`, `status`, `orderDateTime`, `orderLineItems` 를 가진다
  - `type` 은 `TAKEOUT` 이다.
  - `status` 는 다음 목록 중 하나이다.
    - `WAITING`
    - `ACCEPTED`
    - `SERVED`
    - `COMPLETED`
  - `status` 의 상태는 다음 순서로 변경된다.
    - `WAITING` → `ACCEPTED` → `SERVED` → `COMPLETED`
  - `orderLineItems`
    - `OrderLineItem` 의 리스트로, 1개 이상 있어야 한다.
    - `OrderLineItem` 에 포함된 `Menu`는 미리 생성되어 있어야 한다.
    - 비노출 `Menu`가 포함된 `OrderLineItem`은 없어야한다.

##### use-case

- 포장 주문을 생성할 수 있다.
  - `status`는 `WAITING` 이다.
- 포장 주문을 수락할 수 있다.
  - `status`는 `WAITING` 이어야 한다.
  - `status`를 `ACCEPTED`로 변경한다.
- 포장 주문을 서빙할 수 있다.
  - `status`는 `ACCEPTED` 이어야 한다.
  - `status`를 `SERVED`로 변경한다.
- 배달 주문을 완료 할 수 있다.
  - `status`는 `SERVED` 이어야 한다.
  - `status`를 `COMPLETED`로 변경한다.

#### 매장 주문

##### 속성

- `type`, `status`, `orderDateTime`, `orderLineItems`, `orderTable`  를 가진다
  - `type` 은 `EAT-IN` 이다.
  - `status` 는 다음 목록 중 하나이다.
    - `WAITING`
    - `ACCEPTED`
    - `SERVED`
    - `COMPLETED`
  - `status` 의 상태는 다음 순서로 변경된다.
    - `WAITING` → `ACCEPTED` → `SERVED` → `COMPLETED`
  - `orderLineItems`
    - `OrderLineItem` 의 리스트로, 1개 이상 있어야 한다.
    - `OrderLineItem` 에 포함된 `Menu`는 미리 생성되어 있어야 한다.
    - 비노출 `Menu`가 포함된 `OrderLineItem`은 없어야한다.
  - `orderTable`
    - 테이블이 점유 중이어야 한다.
    - 테이블은 미리 생성되어 있어야 한다.
    
##### use-case

- 매장 주문을 생성할 수 있다.
  - `status`는 `WAITING` 이다.
- 매장 주문을 수락할 수 있다.
  - `status`는 `WAITING` 이어야 한다.
  - `status`를 `ACCEPTED`로 변경한다.
- 매장 주문을 서빙할 수 있다.
  - `status`는 `ACCEPTED` 이어야 한다.
  - `status`를 `SERVED`로 변경한다.
- 매장 주문을 완료 할 수 있다.
  - `status`는 `DELIVERED` 이어야 한다.
  - `status`를 `COMPLETED`로 변경한다.
  - 테이블을 비워야 한다.


### 주문한 메뉴 (`OrderLineItem`)

- `OrderLineItem` 는 `Menu` 와 `quantity` 를 가진다.
  - `OrderLineItem` 의 `Menu` 는 생성되어 있어야 한다.
  - `DELIVERY`, `TAKEOUT` 유형의 `Order` 는 수량이 0 이상이어야 한다.
