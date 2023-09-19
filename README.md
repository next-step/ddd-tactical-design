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
- 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨지고 주문이 불가능해진다.
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
- 포장 및 매가`장 주문의 경우 서빙된 주문만 완료할 수 있다.
- 주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.
- 완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.
- 주문 목록을 조회할 수 있다.

## 공통

| 한글명 | 영문명       | 설명                               |
|-----|-----------|----------------------------------|
| 등록  | register  | 신규 OO을 등록함을 뜻한다. (상품, 메뉴, 메뉴 그룹) |
| 비속어 | profanity | 부적절한 용어                          |
| 수량  | quantity  | 수량                               |

## 상품

| 한글명    | 영문명                | 설명                  |
|--------|--------------------|---------------------|
| 상품     | product            | 매장에서 판매하는 음식        |
| 상품 가격  | product price      | 음식의 가격, 0원 이상이어야한다. |
| 상품 이름  | product name       | 음식의 이름              |
| 상품 목록  | product list       | 음식이 나열된 목록          |
| 등록된 상품 | registered product | 상품 목록에 등록된 상품       |

## 메뉴

| 한글명      | 영문명             | 설명                  |
|----------|-----------------|---------------------| 
| 메뉴       | menu            | 주문의 판매 단위           |
| 메뉴 가격    | menu price      | 메뉴의 가격, 0원 이상이어야한다. |
| 메뉴 이름    | menu name       | 메뉴의 이름              |
| 메뉴 그룹    | menu group      | 메뉴를 그룹핑하는데 사용       |
| 메뉴 상품    | menu product    | 메뉴에 포함된 상품 및 갯수 정보  |
| 메뉴 그룹 이름 | menu group name | 메뉴 그룹의 이름           |
| 노출 메뉴    | displayed menu  | 노출중인 메뉴             |
| 숨김 메뉴    | hided menu      | 숨김처리가 된 메뉴          |
| 메뉴 그룹 목록 | menu group list | 메뉴 그룹이 나열된 목록       |

## 주문

| 한글명      | 영문명             | 설명                                          |
|----------|-----------------|---------------------------------------------|
| 주문       | order           | 구매 요청 단위이고 주문은 여러 주문 항목을 가진다.               |
| 주문한 메뉴   | ordered menu    | 주문 안에 포함된 메뉴들을 지칭한다.                        |
| 주문       | order list      | 주문이 나열된 목록                                  |
| 주문 가격    | order price     | 주문한 '메뉴 x 메뉴 가격' 을 합한 금액                    |
| 주문유형     | order type      | 주문의 분류 유형을 뜻한다. (DELIVERY, TAKEOUT, EAT_IN) |
| 주문 항목    | order line item | 주문에 포함된 메뉴 정보 (메뉴, 수량, 가격)                  |
| 주문 접수 대기 | WAITING         | 주문하고 접수 대기중인 상태                             |
| 주문 접수    | ACCEPTED        | 주문을 접수한 상태                                  |
| 서빙완료     | SERVED          | 주문을 손님 or 배달원에게 서빙한 상태                      |
| 주문 완료    | COMPLETED       | 주문 절차가 완료된 상태                               |

## 배달주문

| 한글명    | 영문명              | 설명                   |
|--------|------------------|----------------------|
| 배달 주문  | delivery order   | 배달 주소로 배달원이 배달해주는 주문 |
| 배달 주소  | delivery address | 배달 받기를 원하는 주소        |정
| 배달 중   | DELIVERING       | 배달원이 주문을 배달하는 상태     |
| 배달 완료  | DELIVERED        | 배달원이 배달을 완료한 상태      |
| 배달원    | delivery manager | 배달원                  |
| 배달 대행사 | delivery agency  | 배달을 수행하는 업체          |

## 매장주문

| 한글명      | 영문명                | 설명                               |
|----------|--------------------|----------------------------------|
| 매장 주문    | eat in order       | 손님이 매장에서 식사하는 주문                 |
| 방문한 손님 수 | number of guests   | 주문 테이블을 점유중인 손님 수                |
| 주문 테이블   | order table        | 매장내에 위치한 테이블을 뜻한다. 매장 주문을 할 수있다. |
| 주문 테이블   | order table list   | 주문 테이블을 나열한 목록                   |
| 빈 테이블    | order table empty  | 사용하지 않는 주문 테이블                   |
| 사용중인 테이블 | order table in use | 사용중인 주문 테이블                      |

## 포장주문

| 한글명   | 영문명           | 설명                  |
|-------|---------------|---------------------|경
| 포장 주문 | takeout order | 손님이 직접 포장해서 가져가는 주문 |

## 모델링

### 상품 (Product)

#### 속성
* Name은 공백과 비속어를 허용하지 않고 필수값이다.
* Price는 0원 이상이고 필수값이다.

#### 행위
* Product를 등록할 수 있다.
* Price를 변경할 수 있다.
  * 메뉴의 가격이 메뉴에 속한 Product Price들의 합보다 크면 메뉴가 숨김 처리된다.
* Product List를 조회할 수 있다.

### 메뉴 그룹
#### 속성
* Name은 공백을 허용하지 않고 필수값이다.

#### 행위
- Menu Group을 등록할 수 있다.
- Menu Group List를 조회할 수 있다.

### 메뉴
#### 속성
* Name은 공백과 비속어를 허용하지 않고 필수값이다.
* Menu Group은 필수 값이고 메뉴그룹 목록에 존재해야한다.
* Price는 0원 이상이고 필수값이며 가격정책을 가진다.
  * 가격정책: Menu Product의 Price * Quantity 총합보다 클 수 없다.
* Menu Product를 최소 1개이상 가진다.
* Menu Product의 Product는 상품 목록에 존재해야한다.
* displayed 속성에 따라서 노출 상태를 변경할 수 있다.

#### 행위
- 1개이상의 Registered Product으로 Menu를 등록할 수 있다.
- Menu Price를 변경할 수 있다.
  - 가격 정책을 준수해야한다.
- Menu를 노출(=display)할 수 있다.
  - 가격 정책을 준수해야한다.
- Menu를 숨길(= hide) 수 있다.
- Menu List를 조회할 수 있다.

### 주문 (공통)
#### 속성
- OrderType은 3가지(DELIVERY, TAKEOUT, EAT_IN)중 1개이고 필수값이다.
- Order Date Time을 필수값으로 가진다.
- 숨김처리된 Menu는 주문할 수 없다.

#### 행위
- Order List를 조회할 수 있다.

### 포장주문
#### 속성
- Order Line Item 1개 이상 가진다.
- 4개의 Order Status(WAITING, ACCEPTED, SERVED, COMPLETED) 중 1개를 필수값으로 가진다.
- OrderStatus는 WAITING > ACCEPTED > SERVED > COMPLETED 순서대로만 변경할 수 있다.

#### 행위
- Takeout Order를 등록할 수 있다.
  - Order Status는 WAITING 상태가 된다.
- Takeout Order를 WAITING에서 수락하면 ACCEPTED가 된다.
- Takeout Order를 ACCEPTED에서 서빙하면 SERVED가 된다.
- Takeout Order를 SERVED에서 완료하면 COMPLETED가 한다.

### 배달주문
#### 속성
- Order Line Item 1개 이상 가진다.
- 6개의 Order Status(WAITING, ACCEPTED, SERVED, DELIVERING, DELIVERED, COMPLETED) 중 1개를 필수값으로 가진다.
- Delivery Address는 공백을 허용하지 않는 필수값이다.
- OrderStatus는 WAITING > ACCEPTED > SERVED > **DELIVERING** > **DELIVERED** > COMPLETED 순서대로만 변경할 수 있다.

#### 행위
- Delivery Order를 등록할 수 있다.
  - Order Status는 WAITING 상태가 된다.
- Delivery Order를 WAITING에서 수락하면 ACCEPTED가 된다.
  - 배달 대행사(= Delivery Agency)를 호출(=call)한다.
- Delivery Order를 ACCEPTED에서 서빙하면 SERVED가 된다.
- Delivery Order를 SERVED에서 배달 시작하면 DELIVERING이 한다.
- Delivery Order를 DELIVERING에서 배달 완료하면 DELIVERED 한다.
- Delivery Order를 DELIVERED에서 주문 완료하면 COMPLETED 한다.


### 매장주문(Eat In Order)

#### 속성
- Order Line Item 0개 미만일 수 있다.
- 4개의 Order Status(WAITING, ACCEPTED, SERVED, COMPLETED) 중 1개를 필수값으로 가진다.
- Order Table이 필수다.
  - 빈 테이블에서는 주문을 할 수 없다.
- Order Status는 WAITING > ACCEPTED > SERVED > COMPLETED 순서대로만 변경할 수 있다.

#### 행위
- Eat In Order를 등록할 수 있다.
  - Order Status는 WAITING 상태가 된다.
- Eat In Order를 WAITING에서 수락하면 ACCEPTED가 된다.
- Eat In Order를 ACCEPTED에서 서빙하면 SERVED가 된다.
- Eat In Order를 SERVED에서 완료하면 COMPLETED가 한다.
  - Order Table의 모든 Eat In Order이 COMPLETED 상태가 되면 Empty Table로 변경된다.

### 주문 테이블
#### 속성
- Name은 공백을 허용하지 않고 필수값이다.
- Occupied 속성에 따라서 테이블이 사용중인지 비어있는지 여부를 알 수 있다.
- Number Of Guests를 가진다.
- Number Of Guests는 빈 테이블에서는 변경할 수 없다.
- Number Of Guests는 1명 이상이어야 한다.

#### 행위
- Order Table을 등록할 수 있다.
- Order Table을 사용중인 테이블로 변경할 수 있다.
- Order Table을 빈 테이블로 변경할 수 있다.
  - 완료되지 않은 주문이있다면 빈 테이블로 변경할 수 없다.
- Number Of Guests를 변경할 수 있다.
- Order Table List를 조회할 수 있다.
