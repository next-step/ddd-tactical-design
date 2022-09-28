## 용어 사전

### 상품

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 이름 | displayed name | 음식을 상상하게 만드는 중요한 요소 |
| 가격 | price | 상품의 가격을 의미한다. |
| 비속어 | profanity | 욕설 또는 비속어로 손님들이 불편함을 느껴 상품명에 포함될 수 없는 단어를 의미한다. |

## 모델링

### 상품

- `Product`는 식별자와 `DisplayedName`, `price`을 가진다.<br/><br/>
- `Product`을 생성한다.
  - `DisplayedName`에는 `Profanity`가 포함될 수 없다.
  - `Price`은 0원 이상이어야 한다.
- `Price`을 변경할 수 있다.
