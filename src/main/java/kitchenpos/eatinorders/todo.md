
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
