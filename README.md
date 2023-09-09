# 키친포스

## 퀵 스타트

```sh
cd docker
docker compose -p kitchenpos up -d
```

## 요구 사항

- 간단한 포스기 프로그램을 구현한다.
- 상품
  - [ ] 상품의 가격은 0원보다 커야한다.
  - [ ] 상품을 등록할수 있다.
    - [ ] 상품의 이름은 반드시 있어야 한다.
    - [ ] 상품의 이름은 비속어를 넣을 수 없다.
      - 비속어 검사는 https://www.purgomalum.com/service/containsprofanity?text={{검사할 이름}} 를 통해 확인한다.
  - [ ] 상품의 가격을 수정할수 있다.
    - [ ] 상품 가격을 수정한 상품이 포함된 메뉴의 가격이, 메뉴에 포함된 상품들의 총가격(단가 * 수량)보다 크다면 해당 메뉴를 노출시키지 않는다.
  - [ ] 모든 상품의 정보를 조회할수 있다.

- 메뉴그룹
  - [ ] 메뉴그룹을 등록할 수 있다
    - [ ] 메뉴그룹 이름은 반드시 있어야 한다.
  - [ ] 모든 메뉴그룹의 정보를 조회할수 있다.

- 메뉴
  - [ ] 메뉴의 가격은 0원보다 커야한다.
  - [ ] 메뉴의 가격은 포함된 상품들의 총가격(단가 * 수량)보다 클수 없다.
  - [ ] 메뉴를 등록할수 있다.
    - [ ] 메뉴는 상항 메뉴그룹에 속해있어야 한다.
    - [ ] 없는 상품은 메뉴에 추가할수 없다.
    - [ ] 메뉴는 최소 하나 이상의 상품을 가지고 있어야 한다.
    - [ ] 메뉴에 포함된 상품은 각각 0개 이상 등록할 수 있다.
  - [ ] 메뉴 가격을 변경할수 있다.
  - [ ] 메뉴 가격이, 포함된 상품들의 총 가격(단가 * 수량)보다 적은 메뉴는 노출시킬수 있다.
  - [ ] 메뉴를 숨길수 있다.
  - [ ] 메뉴의 모든 정보를 조회할수 있다.

- 테이블
  - [ ] 테이블을 등록할수 있다.
    - [ ] 테이블은 이름, 테이블에 앉은 손님 수, 테이블의 상태를 가진다.
      - [ ] 테이블의 상태는 총 2가지다
        - 사용중
        - 사용중 아님
    - [ ] 등록된 직후의 테이블은 초기화가 되있어야 한다.
      - 초기화란 테이블에 "손님이 앉아 있지 않고", 테이블의 상태는 "사용중 아님"이여야 한다.
  - [ ] 테이블의 상태를 "사용중"으로 변경할수 있다.
  - [ ] 테이블을 초기화 할수 있어야 한다.
    - [ ] 초기화 할 테이블에서 요청한 모든 주문상태가 "완료됨" 이여야 초기화 할수 있다.
  - [ ] 테이블에 손님을 앉힐수 있다.
    - [ ] 손님 수는 0명 이상이여야 한다.
    - [ ] 테이블 상태가 "사용중" 인 테이블에만 손님을 앉힐수 있다.
  - [ ] 테이블의 모든 정보를 조회할수 있다.

- 주문
  - [ ] 주문은 주문 유형, 상태, 주문 시간, 주문 품목들, 배송 주소, 주문 테이블 정보를 가진다.
    - [ ] 주문 유형은 총 세 가지다.
      - DELIVERY: 배달
      - TAKEOUT: 포장
      - EAT_IN: 매장 식사
    - [ ] 주문 상태는 총 여섯 가지다.
      - WAITING: 대기 중
      - ACCEPTED: 접수 완료
      - SERVED: 서빙 중
      - DELIVERING: 배달 중
      - DELIVERED: 배달 완료
      - COMPLETED: 완료됨
  - [ ] 주문을 등록할수 있다.
    - [ ] "주문유형"은 반드시 있어야 한다.
    - [ ] 최소한 하나의 메뉴는 주문해야 한다.
    - [ ] 존재하지 않은 메뉴는 주문할수 없다.
    - [ ] 노출되지 않은 메뉴는 주문할수 없다.
    - [ ] 저장된 메뉴의 가격과 주문한 메뉴의 가격이 다를수 없다.
    - [ ] "배달주문"과 "포장주문"이라면 메뉴 별로 0개 이상 주문해야 한다.
    - [ ] 주문 상태는 "대기 중" 으로 초기화 되어야 한다.
    - [ ] "배달주문"은 "배달주소"가 반드시 있어야 한다.
    - [ ] "매장식사 주문"은 "사용중인 테이블"에서만 주문할수 있다.
  - [ ] 주문을 수락할수 있다.
    - [ ] "대기 중" 이 아닌 주문은 수락할수 없다.
    - [ ] "배달주문"은 "주문ID", "주문금액", "배달주소" 정보를 라이더에 전달해야한다.
    - [ ] 주문 상태는 "접수 완료"로 변경되어야 한다.
  - [ ] 주문을 서빙할수 있다.
    - [ ] "접수 완료"된 주문만 서빙할수 있다.
    - [ ] 주문 상태는 "서빙 중"으로 변경되어야 한다.
  - [ ] 주문의 배달을 시작할수 있어야 한다.
    - [ ] "배달주문"만 배달할수 있다.
    - [ ] "서빙중인 주문"만 배달할수 있다.
    - [ ] 주문 상태는 "배달 중"으로 변경되어야 한다.
  - [ ] 주문의 배달을 완료시킬수 있어야 한다.
    - [ ] "배달 중"인 주문만 배달 완료할수 있다.
    - [ ] 주문 상태는 "배달 완료"로 변경되어야 한다.
  - [ ] 주문을 완료처리 할수 있어야 한다.
    - [ ] "배달주문"은 배달이 되어야 완료처리 할수 있다.
    - [ ] "매장식사 주문", "포장주문"은 서빙이 되어야 완료처리 할수 있다.
    - [ ] "매장식사 주문"은 완료처리와 동시에 앉았던 테이블을 초기화 해주어야 한다.
    - [ ] 주문 상태는 "완료됨"으로 변경되어야 한다.
  - [ ] 주문의 모든 정보를 조회할수 있어야 한다.
---

## 용어 사전

### 상품
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 판매할 수 있는 단일 식품 |
| 이름 | name | 상품의 이름 |
| 가격 | price | 상품의 가격(₩) |
| 등록 | create | 포스기에 신규로 상품을 등록한다 |
| 가격 변경 | change price | 등록된 상품의 가격을 변경한다  |

### 메뉴 그룹
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 메뉴 그룹 |  menu group | 메뉴들을 종류에 따라 모아둔 그룹 |
| 이름 | name | 메뉴 그룹의 이름 |
| 등록 | create | 포스기에 신규로 메뉴 그룹을 등록한다 |

### 메뉴
| 한글명 | 영문명 | 설명                      |
| --- | --- |-------------------------|
| 메뉴 | menu | 고객들에게 제공되는 구성품들과 가격의 정보 |
| 이름 | name | 메뉴의 이름                  |
| 가격 | price | 메뉴의 가격(₩)               |
| 구성품 | menu product | 메뉴를 구성하고 있는 상품들         |
| 등록 | create | 포스기에 신규로 메뉴를 등록한다       |
| 활성화 | display | 메뉴를 노출시킨다               |
| 비활성화 | hide | 메뉴를 노출시키지 않는다           |
| 가격 변경 | change price | 등록된 메뉴의 가격을 변경한다        |

### 매장 테이블
| 한글명     | 영문명 | 설명                      |
|---------| --- |-------------------------|
| 매장 테이블  |  order table | 매장 내 고객이 이용할 수 있는 테이블   |
| 이름      | name | 매장 테이블을 지칭하는 이름         |
| 고객 수    | number of guest | 매장 테이블을 이용 중인 인원 수      |
| 이용 중    | occupied | 매장 테이블이 이용 중인 상태        |
| 이용중이 아님 | vacant | 매장 테이블이 이용 되지 않는 상태     |
| 등록      | create | 포스기에 신규로 매장 테이블을 등록한다   |
| 안내      | sit | 고객이 매장테이블을 이용할수 있게 안내한다 |
| 정리      | clear | 매장 테이블을 비운다             |
| 고객 수 변경 | change number of guests | 매장 테이블을 이용중인 인원 수를 변경한다 |

### 주문
| 한글명      | 영문명               | 설명                                      |
|----------|-------------------|-----------------------------------------|
| 주문       | order             | 고객이 선택한 메뉴들                             |
| 배달 주문    | delivery order    | 배달 대행 업체를 통해 음식을 배달하는 형태의 주문            |
| 매장 식사 주문 | eat-in order      | 매장 내에서 식사하는 형태의 주문                      |
| 포장 주문    | takeout order     | 매장에서 포장하여 고객이 외부에서 먹는 형태의 주문            |
| 접수 대기 중  | WAITING           | 주문이 접수되어 사장님/직원분의 수락 대기중인 상태            |
| 수락됨      | ACCEPTED          | 접수된 주문을 포스기를 통해 수락한 상태                  |
| 전달됨      | SERVED            | 매장에서 주문한 구성품이 전달된 상태                    |
| 배달 중     | DELIVERING        | 주문이 배달 중인 상태                            |
| 배달 완료    | DELIVERED         | 배달이 완료된 상태                              |
| 완료됨      | COMPLETED         | 주문에 대한 모든 처리가 완료된 상태                    |
| 배달 대행 업체      | kitchen riders    | 배달기사 배정 및 배달을 대행해주는 업체                  |
| 주문 일자    | order datetime    | 고객이 주문한 날짜와 시간                          |
| 주문 내역    | order line items  | 고객이 주문한 메뉴들                             |
| 배달 요청    | request delivery  | 배달주문이 수락될때 배달 대행 업체에게 배달을 요청한다          |
| 등록       | create            | 고객이 매장에 주문을 한다                          |
| 수락       | accept            | 직원이 접수된 주문을 포스기를 통해 수락한다                |
| 전달       | serve             | 고객이 주문한 구성품을 전달한다                   |
| 배달 시작    | start delivery    | 배달 대행 업체가 배달을 시작한다                      |
| 배달 완료    | complete delivery | 배달 대행 업체가 배달을 완료한다                      |
| 완료       | complete          | 주문에 대한 모든 처리가 끝나, 직원이 포스기를 통해 주문을 완료 한다 |

### 공통
| 한글명 | 영문명       | 설명                                  |
|-----|-----------|-------------------------------------|
| 비속어 | profanity | 욕설이나 부적절한 언어                        |
| 직원  | manager   | 매장에서 근무하는 포스기를 조작 가능한 사장님 혹은 아르바이트생 |

---

## 모델링 (Product)
### 상품(Product)
#### 속성
- `Product` 는 `Price`를 가진다
- `Product` 는 `profanity`가 아닌 `Name`을 가진다

#### 기능
- `Product`를 `Create`할수 있다
- `Product`를 `Change Price`할수 있다
  - `Product`를 `MenuProduct`로 가지고 있는 `Menu`의 `Price`가 `MenuProduct`들의 `TotalPrice`를 넘으면 `Menu`를 `Hide`한다

---

## 모델링 (Menu)
### 메뉴(Menu)
#### 속성
- `Menu`는 `Profanity`가 아닌 `Name`을 가진다
- `Menu`는 `MenuProduct`들의 `TotalPrice`보다 작은 `Price`를 가진다
- `Menu`는 `MenuGroup`을 가진다
- `Menu`는 `MenuProduct`들을 가진다
- `Menu`는 노출 여부인 `display`, `hide`상태를 가진다

#### 기능
- `Menu`는 `Create`할수 있다
- `Menu`는 `Change Price`를 할수 있다
- `Menu`는 `Hide`할수 있다
- `Menu`는 `Display`할수 있다
- `MenuProduct`들의 `TotalPrice`를 계산할수 있다

### 메뉴 그룹(MenuGroup)
#### 속성
- `MenuGroup`은 `Name`을 가진다

### 구성품 (MenuProduct)
#### 속성
- `MenuProduct`는 `Product`를 가진다
- `MenuProduct`는 `Product`의 수량인 `Quantity`를 가진다
- `MenuProduct`는 자신이 포함될 `Menu`를 가진다

---

## 모델링 (EatInOrder)
### 매장 식사 주문(EatInOrder)
#### 속성
- `EatInOrder`는 `Occupied`된 `OrderTable`을 가진다
- `EatInOrder`는 `Order Datetime`을 가진다
- `EatInOrder`는 `Order Line Items`를 가진다

#### 기능
- `EatInOrder`는 `WAITING`, `ACCEPTED`, `SERVED`, `COMPLETED` 순으로 진행된다
- `EatInOrder`가 `COMPLETED`될때, `OrderTable`에 모든 `Order`가 `COMPLETED` 상태라면 `OrderTable`을 `Clean`한다
- `EatInOrder`는 `OrderLineItem`을 취소할 수 있다

### 매장 테이블(OrderTable)
#### 속성
- `OrderTable`은 `Name`을 가지고 있다
- `OrderTable`은 이용중인 `NumberOfGuest`을 가지고 있다
- `OrderTable`은 `Name`을 가지고 있다
- `OrderTable`은 `Occupied`와 `Vacant` 상태를 가질수 있다

#### 기능
- `OrderTable`은 `Clear`할수 있다
- `OrderTable`을 `Occupied`할수 있다
- `OrderTable`은 `Change Number Of Guest`할수 있다

---

## 모델링 (TakeOutOrder)
### 포장 주문(TakeoutOrder)
#### 속성
- `TakeoutOrder`는 `Order Datetime`을 가진다
- `TakeoutOrder`는 `Order Line Items`를 가진다
#### 기능
- `TakeoutOrder`는 `WAITING`, `ACCEPTED`, `SERVED`, `COMPLETED` 순으로 진행된다

---

## 모델링 (DeliveryOrder)
### 배달 주문(DeliveryOrder)
#### 속성
- `DeliveryOrder`는 `DeliveryAddress`를 가진다
- `DeliveryOrder`는 `Order Datetime`을 가진다
- `DeliveryOrder`는 `Order Line Items`를 가진다

#### 기능
- `DeliveryOrder`는 `WAITING`, `ACCEPTED`, `SERVED`, `DELIVERING`, `DELIVERED`, `COMPLETED`순으로 진행된다
- `DeliveryOrder`는 `Kitchen Riders`를 통해 `Start Delivery`해야 한다








