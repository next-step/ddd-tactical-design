# Menu Context(메뉴 컨텍스트)

## 용어 사전

### MenuGroup (메뉴그룹)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 메뉴그룹 | Menu Group | 메뉴를 모아놓은 그룹. ex) 식사류, 면류, 사이드 등 메뉴를 분류할 때 사용 |
| 이름 | Name | 메뉴그룹의 이름 |

### Menu (메뉴)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 메뉴 | Menu | 메뉴 그룹에 속하는 실제 주문 가능 단위. 하나의 메뉴는 하나이상의 상품으로 구성 |
| 가격 | Price | 메뉴의 가격. `메뉴의 가격 <= 총 금액`을 지켜야한다. |
| 메뉴상품 | Menu Product | 메뉴에 포함된 상품. 수량을 가지고 있디. 각 메뉴 상품의 가격은 (상품가격 * 수량) 이다 |
| 금액 | Amount | 메뉴 상품의 가격 * 수량의 결과. |
| 총 금액 | Total Amount | 메뉴가 가진 모든 메뉴 상품의 (가격 * 수량) 총합. |
| 이름 | Name | 메뉴의 이름 |
| 노출 | Display | 메뉴를 고객에게 노출 |
| 비노출 | Hide | 메뉴를 고객에게 비노출. 메뉴의 가격이 메뉴 상품들의 총 금액 높은경우 비노출된 상태가 될 수 있다. |
| 비속어 | profanity | 고객에게 불쾌함을 줄 수 있는 언어. 메뉴의 이름은 비속어를 포함하지 않아야한다. |

### Profanity (비속어)

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 비속어 검사 | Profanity Check | 욕설등의 부적절한 언어가 포함되어있는지 purgomalum 외부 서비스를 통해 contains profanity 검사 |

---

## 모델링

### MenuGroup (메뉴 그룹)

#### 속성

- Name은 필수값이다.

#### 행위

- MenuGroup을 등록할 수 있다.

### Menu (메뉴)

#### 속성

- Name은 필수값이다.
- Price는 0원 이상이어야 한다.
  - Menu의 가격은 Menu에 속한 상품 금액의 합보다 작거나 같아야 한다.
- Menu Group을 필수로 가진다.
- Menu Product를 필수로 1개이상 가진다.
- Menu Product의 Price의 총합보다 클 수 없다.
- Menu의 Displayed는 변경 가능하다.

#### 행위

- Menu를 등록할 수 있다.
  - Name은 Profanity Check한다.
- Price는 변경할 수 있다.
- Menu를 Display한다.
  - Menu의 Price가 Menu에 속한 Menu Product 금액의 합보다 비싼 경우 Menu를 Display할 수 없다.
- Menu를 Hide한다.
  - Menu의 Price가 Menu에 속한 Product Price의 합보다 높을 경우 Menu를 Hide한다.
