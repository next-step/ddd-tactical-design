# 매장주문

## 모델링
#### 속성
- `OrderTable`은 식별자, 이름, `Number Of Guests`를 갖는다.
- `Number Of Guests`는 손님 수를 갖는다.
  - 손님 수는 0명 이상이어야 한다.
#### 기능
- `OrderTable`을 등록할 수 있다.
  - `OrderTable` 등록 시, 테이블 상태는 `Not In Use`이다.
- `OrderTable`은 `Use Table` 할 수 있다.
  - 테이블 상태를 `In Use`로 변경한다.
- `OrderTable`은 `Clean Table` 할 수 있다.
  - `Clean Table` 시 Eat In Order Complete Policy를 따른다.
  - 테이블 상태를 `Not In Use`로 변경과 `Number Of Guests`를 0으로 만든다.
- `OrderTable`의 `Number Of Guests`를 변경 할 수 있다.
  - 테이블 상태가 `In Use`이어야 변경할 수 있다.
  
#### 속성
- `EatInOrder`는 `OrderTable`의 식별자, `OrderStatus`, 주문시각, `OrderLineItems`를 갖는다.
- `OrderStatus`는 `Waiting`, `Accepted`, `Served`, `Completed` 을 갖는다.
  - `OrderStatus`는 순서를 갖는데, (`Waiting` > `Accepted` > `Served` > `Completed`)와 같이 진행된다.
- Quantity는 수량을 갖는다.
  - 수량은 0개 이상이어야 한다.
- `OrderLineItems`는 `OrderLineItem`을 갖는다.
- `OrderLineItem`은 `Menu`의 식별자, `Price`, Quantity를 갖는다.
  - `OrderLineItem`의 `Price`와 `Menu`의 `Price`는 같아야한다.
#### 기능
- `EatInOrder`는 생성할 수 있다.
  - `EatInOrder` 생성 시, `OrderTable`은  `In Use` 여야 한다.
  - `Menu`는 Displayed Menu 여야 한다.
- `EatInOrder`는 접수할 수 있다.
- `EatInOrder`는 서빙할 수 있다.
- `EatInOrder`는 완료할 수 있다.
  - `EatInOrder`가 완료될 때, EatInOrder Complete Policy를 따른다.

#### 정책
- 매장주문완료정책(EatInOrderCompletePolicy)
  - `EatInOrder`가 Complete 되었을때, 해당 `EatInOrder`와 관련된 `OrderTable`의 모든 `EatInOrder`가 `Completed`면 `Clean Table`한다


## 용어사전
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 테이블 | Order Table | 매장 주문을 위해 방문한 손님들이 차지해야하는 테이블 |
| 손님 수 | Number of Guests | 테이블을 사용하고 있는 손님 수를 말한다 |
| 테이블 상태 | TableStatus | 테이블 사용유무 |
| 사용중 | In Use | 테이블을 사용하는 상태 |
| 사용안함 | Not In Use | 테이블을 사용하지 않는 상태 |
| 테이블 정리 | Clean Table | 테이블 상태를 사용안함으로 변경하는 것 |
| 테이블 사용 | Use Table | 테이블 상태를 사용중으로 변경하는 것 |
| 테이블 목록 조회 | View All Tables | 전체 테이블을 조회하는 것을 말한다 |
| 주문 | Eat In Order | 음식을 매장에서 먹고갈 경우에 하는 주문 |
| 주문 항목 | Order Line Item | 한 번의 주문에 1개 이상의 메뉴로 주문할 수 있는데, 각각의 주문한 메뉴 항목을 말한다 |
| 주문 항목들 | Order Line Items | Order Line Item 의 묶음을 말한다 |
| 주문 상태 | OrderStatus | 주문의 진행상태 |
| 대기중 | Waiting | 매장 주문의 처음 상태 |
| 접수됨 | Accepted | 접수 대기 중인 주문을 확인하고 주문을 접수한 상태 |
| 서빙됨 | Served | 접수된 주문을 음식을 준비하여 서빙한 상태 |
| 완료됨 | Completed | 주문의 마지막 상태. 주문이 손님에게 제공 완료되었음을 의미한다 |
