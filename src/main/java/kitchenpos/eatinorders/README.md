## 용어 사전

### 매장 주문

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 매장 주문 | eatInOrder | 매장에서 식사하기 위해 한 종류 이상의 메뉴를 구매하는 것을 의미한다. 고객이 요청한 주문상품 정보들을 말한다. |
| 메뉴 | menu | 주문할 수 있는 최소 단위 |
| 전시된 메뉴 | displayedMenu | 주문할 수 있는 메뉴 |
| 숨겨진 메뉴 | hiddenMenu | 주문할 수 없는 메뉴 |
| 주문 상품 | orderLineItem | 주문하려는 메뉴의 종류와 개수 정보를 포함하는 정보다. |
| 주문 상태 | orderStatus | 주문의 진행 상황을 의미한다.<br/>대기, 수락, 서빙완료, 주문 처리 완료가 있다. |
| 대기 | waiting | 손님이 주문을 하고 사장님이 수락하지 않은 상태 |
| 수락 | accepted | 손님의 주문을 사장님이 수락한 상태 |
| 서빙 완료 | served | 손님이 주문한 음식이 주문 테이블에 전달된 상태 |
| 주문 처리 완료 | completed | 음식이 손님에게 전달되어 사장님이 주문을 완료 처리한 상태 |
| 주문 시간 | orderDateTime | 주문을 등록한 시간이다. |
| 수락한다 | accept | 고객의 주문을 수락한다. |
| 서빙한다 | serve | 주문 테이블에 음식을 서빙한다. |
| 완료한다 | complete | 주문을 완료한다. |
| 주문 테이블 | orderTable | 손님들이 매장 식사를 하기 위해 앉을 테이블을 의미한다. |
| 주문테이블명 | name | 주문 테이블의 이름이다. |
| 손님 수 | numberOfGuests | 테이블에 앉은 손님의 수다. |
| 사용 여부 | occupied | 테이블이 사용중인지 여부를 나타낸다.<br/>ex) `사용중` : 손님들에게 배정이 된 테이블로, 손님에게 배정을 할 수 없음을 의미한다.<br/>`사용 가능` : 비어있는 테이블로 손님에게 배정을 할 수 있음을 의미한다. |
| 사용한다 | use | 주문 테이블을 `사용 중` 으로 바꾼다. |
| 치운다 | clear | 주문 테이블을 `사용 가능` 으로 바꾼다. |
| 손님 수를 변경한다 | changeNumberOfGuests | 주문 테이블에 앉아있는 손님의 수를 변경한다. |

## 모델링

### 매장 주문

- 속성
  - 식별자
  - 주문 상태
    - 대기, 수락, 서빙완료, 주문처리 완료를 가진다.
  - 주문 시간
  - 주문 상품 목록
    - 주문 상품이 하나 이상이어야 한다.
    - 주문 상품
      - 주문 상품은 가격과 수량을 가진다.
      - 수량은 기존 `Order`를 취소하거나 변경해도 수정되지 않기 때문에 0보다 적을 수 있다.

- 행위
  - `매장 주문(eatInOrder)`은 `대기(waiting)` → `수락(accepted)` → `서빙 완료(served)` → `주문 처리 완료(completed)` 순서로 진행된다.
  - `매장 주문(eatInOrder)`을 생성한다.
      - 존재하지 않는 `메뉴(menu)`나 `숨겨진 메뉴(hiddenMenu)`는 주문할 수 없다.
      - `주문상품(orderLineItem)`의 가격은 실제 `메뉴 가격(menuPrice)`과 일치해야 한다.
      - `매장 주문(eatInOrder)`은 `사용중` 상태의 `주문테이블(orderTable)`에서만 이뤄진다.
  - `매장 주문(eatInOrder)`을 `수락한다(accept).`
      - `대기(waiting)` 상태의 `매장 주문(eatInOrder)`만 `수락할 수 있다(accept).`
  - `매장 주문(eatInOrder)`을 `서빙한다(serve).`
      - `수락(accped)` 상태의 `매장 주문(eatInOrder)`만 `서빙할 수 있다(serve).`
  - `매장 주문(eatInOrder)`을 `완료한다(complete).`
      - `서빙 완료(served)` 된 `매장 주문(eatInOrder)`만 `완료할 수 있다(complete).`
      - `주문 테이블(orderTable)`의 모든 `매장 주문(eatInOrder)`이 `주문 처리 완료(completed)` 상태가 되면 `사용 가능` 한 `주문테이블(orderTable)`로 변경한다.
    
### 주문 테이블

- 속성
  - 식별자
  - 이름
    - 이름은 공백이어서는 안된다.
  - 손님 수
    - 손님 수는 0명보다 적을 수 없다.
  - 사용 여부

- 행위
  - `주문 테이블(orderTable)`을 생성한다.
  - `주문 테이블(orderTable)`을 `사용한다(use).`
      - `주문테이블(orderTable)`을 `사용(use)`하면 `사용중` 상태가 된다.
  - `주문 테이블(orderTable)`을 `치운다(clear).`
      - `주문 테이블(orderTable)`에 완료되지 않은 주문이 있으면 `치울 수 없다(clear).`
      - `주문 테이블(orderTable)`을 치우면 `손님 수(numberOfGuests)`는 0이 된다.
      - `주문 테이블(orderTable)`을 `치우면(clear)` `사용 가능` 상태가 된다.
  - `주문 테이블(orderTable)`의 `손님 수(numberOfGuests)`를 `변경한다(changeNumberOfGuests).`
      - 비어있는 `주문 테이블(orderTable)`의 `손님 수(numberOfGuests)`를 변경할 수 없다.
