# Product Context (상품 컨텍스트)

## 용어사전

### Product (상품)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | Product | 메뉴를 구성하는 최소 단위 |
| 이름 | Name | 상품의 이름 |
| 이름 정책 | Name Policy | 상품의 이름을 만드는 정책. |
| 가격 | Price | 상품의 가격 |
| 비속어 | profanity | 고객에게 불쾌함을 줄 수 있는 언어. 상품의 이름은 비속어를 포함하지 않아야한다. |

### Profanity (비속어)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 비속어 검사 | Profanity Check | 욕설등의 부적절한 언어가 포함되어있는지 purgomalum 외부 서비스를 통해 contains profanity 검사 |

---

## 모델링

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
